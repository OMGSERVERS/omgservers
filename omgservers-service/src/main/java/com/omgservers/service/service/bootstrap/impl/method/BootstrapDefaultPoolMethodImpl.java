package com.omgservers.service.service.bootstrap.impl.method;

import com.omgservers.schema.model.alias.AliasModel;
import com.omgservers.schema.model.alias.AliasQualifierEnum;
import com.omgservers.schema.model.pool.PoolModel;
import com.omgservers.schema.model.poolServer.PoolServerConfigDto;
import com.omgservers.schema.model.poolServer.PoolServerQualifierEnum;
import com.omgservers.schema.module.alias.FindAliasRequest;
import com.omgservers.schema.module.alias.FindAliasResponse;
import com.omgservers.schema.module.alias.SyncAliasRequest;
import com.omgservers.schema.module.pool.pool.SyncPoolRequest;
import com.omgservers.schema.module.pool.poolServer.SyncPoolServerRequest;
import com.omgservers.schema.module.pool.poolServer.SyncPoolServerResponse;
import com.omgservers.service.configuration.DefaultAliasConfiguration;
import com.omgservers.service.exception.ServerSideNotFoundException;
import com.omgservers.service.factory.alias.AliasModelFactory;
import com.omgservers.service.factory.pool.PoolModelFactory;
import com.omgservers.service.factory.pool.PoolServerModelFactory;
import com.omgservers.service.module.alias.AliasModule;
import com.omgservers.service.module.pool.PoolModule;
import com.omgservers.service.operation.getConfig.GetConfigOperation;
import com.omgservers.service.operation.getConfig.ServiceConfig;
import com.omgservers.service.operation.getServers.GetServersOperation;
import com.omgservers.service.service.bootstrap.dto.BootstrapDefaultPoolRequest;
import com.omgservers.service.service.bootstrap.dto.BootstrapDefaultPoolResponse;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class BootstrapDefaultPoolMethodImpl implements BootstrapDefaultPoolMethod {

    final AliasModule aliasModule;

    final PoolModule poolModule;

    final GetServersOperation getServersOperation;
    final GetConfigOperation getConfigOperation;

    final PoolServerModelFactory poolServerModelFactory;
    final AliasModelFactory aliasModelFactory;
    final PoolModelFactory poolModelFactory;

    @Override
    public Uni<BootstrapDefaultPoolResponse> execute(final BootstrapDefaultPoolRequest request) {
        log.debug("Bootstrap default pool");

        return findDefaultPoolAlias()
                .replaceWith(Boolean.FALSE)
                .onFailure(ServerSideNotFoundException.class)
                .recoverWithUni(t -> createDefaultPool()
                        .flatMap(defaultPool -> createDefaultPoolServers(defaultPool.getId())
                                .flatMap(voidIteam -> createDefaultPoolAlias(defaultPool.getId())
                                        .invoke(alias -> log.info("The default pool \"{}\" was created",
                                                defaultPool.getId()))))
                        .replaceWith(Boolean.TRUE))
                .map(BootstrapDefaultPoolResponse::new);
    }

    Uni<AliasModel> findDefaultPoolAlias() {
        final var request = new FindAliasRequest(DefaultAliasConfiguration.GLOBAL_SHARD_KEY,
                DefaultAliasConfiguration.GLOBAL_ENTITIES_GROUP,
                DefaultAliasConfiguration.DEFAULT_POOL_ALIAS);
        return aliasModule.getService().execute(request)
                .map(FindAliasResponse::getAlias);
    }

    Uni<PoolModel> createDefaultPool() {
        final var defaultPool = poolModelFactory.create();
        final var request = new SyncPoolRequest(defaultPool);
        return poolModule.getPoolService().execute(request)
                .replaceWith(defaultPool);
    }

    Uni<Void> createDefaultPoolServers(final Long defaultPoolId) {
        final var dockerHosts = getConfigOperation.getServiceConfig()
                .bootstrap().defaultPool().dockerHosts();

        final var serverCounter = new AtomicInteger();
        return Multi.createFrom().iterable(dockerHosts)
                .onItem().transformToUniAndConcatenate(dockerHostConfig ->
                        createPoolServer(defaultPoolId,
                                serverCounter.getAndIncrement(),
                                dockerHostConfig))
                .collect().asList()
                .replaceWithVoid();
    }

    Uni<AliasModel> createDefaultPoolAlias(final Long poolId) {
        final var alias = aliasModelFactory.create(AliasQualifierEnum.POOL,
                DefaultAliasConfiguration.GLOBAL_SHARD_KEY,
                DefaultAliasConfiguration.GLOBAL_ENTITIES_GROUP,
                poolId,
                DefaultAliasConfiguration.DEFAULT_POOL_ALIAS);
        final var request = new SyncAliasRequest(alias);
        return aliasModule.getService().execute(request)
                .replaceWith(alias);
    }

    Uni<Boolean> createPoolServer(final Long defaultPoolId,
                                  final int serverIndex,
                                  final ServiceConfig.BootstrapDockerHostConfig dockerHostConfig) {
        final var poolServerConfig = PoolServerConfigDto.create();
        poolServerConfig.setDockerHostConfig(new PoolServerConfigDto.DockerHostConfig(
                dockerHostConfig.dockerDaemonUri(),
                dockerHostConfig.cpuCount(),
                dockerHostConfig.memorySize(),
                dockerHostConfig.maxContainers()));

        final var poolServer = poolServerModelFactory.create(defaultPoolId,
                PoolServerQualifierEnum.DOCKER_HOST,
                poolServerConfig);

        final var request = new SyncPoolServerRequest(poolServer);
        return poolModule.getPoolService().execute(request)
                .map(SyncPoolServerResponse::getCreated);
    }
}
