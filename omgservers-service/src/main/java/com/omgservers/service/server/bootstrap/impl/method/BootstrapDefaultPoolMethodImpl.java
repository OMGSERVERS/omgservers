package com.omgservers.service.server.bootstrap.impl.method;

import com.omgservers.schema.model.pool.PoolConfigDto;
import com.omgservers.schema.model.pool.PoolModel;
import com.omgservers.schema.model.poolServer.PoolServerConfigDto;
import com.omgservers.schema.model.poolServer.PoolServerQualifierEnum;
import com.omgservers.schema.shard.pool.pool.SyncPoolRequest;
import com.omgservers.schema.shard.pool.poolServer.SyncPoolServerRequest;
import com.omgservers.schema.shard.pool.poolServer.SyncPoolServerResponse;
import com.omgservers.service.configuration.DefaultAliasConfiguration;
import com.omgservers.service.exception.ServerSideNotFoundException;
import com.omgservers.service.factory.alias.AliasModelFactory;
import com.omgservers.service.factory.pool.PoolModelFactory;
import com.omgservers.service.factory.pool.PoolServerModelFactory;
import com.omgservers.service.operation.alias.CreatePoolAliasOperation;
import com.omgservers.service.operation.alias.FindDefaultPoolAliasOperation;
import com.omgservers.service.operation.server.GetServiceConfigOperation;
import com.omgservers.service.operation.server.ServiceConfig;
import com.omgservers.service.server.bootstrap.dto.BootstrapDefaultPoolRequest;
import com.omgservers.service.server.bootstrap.dto.BootstrapDefaultPoolResponse;
import com.omgservers.service.shard.alias.AliasShard;
import com.omgservers.service.shard.pool.PoolShard;
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

    final AliasShard aliasShard;

    final PoolShard poolShard;

    final FindDefaultPoolAliasOperation findDefaultPoolAliasOperation;
    final GetServiceConfigOperation getServiceConfigOperation;
    final CreatePoolAliasOperation createPoolAliasOperation;

    final PoolServerModelFactory poolServerModelFactory;
    final AliasModelFactory aliasModelFactory;
    final PoolModelFactory poolModelFactory;

    @Override
    public Uni<BootstrapDefaultPoolResponse> execute(final BootstrapDefaultPoolRequest request) {
        log.debug("Bootstrapping default pool");

        return findDefaultPoolAliasOperation.execute()
                .replaceWith(Boolean.FALSE)
                .onFailure(ServerSideNotFoundException.class)
                .recoverWithUni(t -> createDefaultPool()
                        .flatMap(defaultPool -> createDefaultPoolServers(defaultPool.getId())
                                .flatMap(voidItem -> createPoolAliasOperation.execute(defaultPool.getId(),
                                        DefaultAliasConfiguration.DEFAULT_POOL_ALIAS)))
                        .replaceWith(Boolean.TRUE))
                .map(BootstrapDefaultPoolResponse::new);
    }

    Uni<PoolModel> createDefaultPool() {
        final var defaultPool = poolModelFactory.create(PoolConfigDto.create());
        final var request = new SyncPoolRequest(defaultPool);
        return poolShard.getService().execute(request)
                .replaceWith(defaultPool);
    }

    Uni<Void> createDefaultPoolServers(final Long defaultPoolId) {
        final var dockerHosts = getServiceConfigOperation.getServiceConfig()
                .bootstrap().defaultPool().servers();

        final var serverCounter = new AtomicInteger();
        return Multi.createFrom().iterable(dockerHosts)
                .onItem().transformToUniAndConcatenate(dockerHostConfig ->
                        createPoolServer(defaultPoolId,
                                serverCounter.getAndIncrement(),
                                dockerHostConfig))
                .collect().asList()
                .replaceWithVoid();
    }

    Uni<Boolean> createPoolServer(final Long defaultPoolId,
                                  final int serverIndex,
                                  final ServiceConfig.BootstrapDefaultPoolServerConfig dockerHostConfig) {
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
        return poolShard.getService().execute(request)
                .map(SyncPoolServerResponse::getCreated);
    }
}
