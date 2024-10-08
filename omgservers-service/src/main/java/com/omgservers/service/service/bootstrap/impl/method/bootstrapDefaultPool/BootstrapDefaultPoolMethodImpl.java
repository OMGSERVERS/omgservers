package com.omgservers.service.service.bootstrap.impl.method.bootstrapDefaultPool;

import com.omgservers.schema.model.pool.PoolModel;
import com.omgservers.schema.model.poolServer.PoolServerConfigDto;
import com.omgservers.schema.model.poolServer.PoolServerQualifierEnum;
import com.omgservers.schema.module.pool.pool.SyncPoolRequest;
import com.omgservers.schema.module.pool.poolServer.SyncPoolServerRequest;
import com.omgservers.schema.module.pool.poolServer.SyncPoolServerResponse;
import com.omgservers.service.factory.pool.PoolModelFactory;
import com.omgservers.service.factory.pool.PoolServerModelFactory;
import com.omgservers.service.module.pool.PoolModule;
import com.omgservers.service.operation.getConfig.GetConfigOperation;
import com.omgservers.service.operation.getConfig.ServiceConfig;
import com.omgservers.service.operation.getServers.GetServersOperation;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.net.URI;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class BootstrapDefaultPoolMethodImpl implements BootstrapDefaultPoolMethod {

    final PoolModule poolModule;

    final GetServersOperation getServersOperation;
    final GetConfigOperation getConfigOperation;

    final PoolServerModelFactory poolServerModelFactory;
    final PoolModelFactory poolModelFactory;

    @Override
    public Uni<Void> bootstrapDefaultPool() {
        log.debug("Bootstrap default pool");

        return createDefaultPool()
                .flatMap(defaultPool -> {
                    final var defaultPoolId = defaultPool.getId();
                    final var defaultPoolConfig = getConfigOperation
                            .getServiceConfig().bootstrap().defaultPool();

                    return getServersOperation.getServers()
                            .flatMap(servers -> Multi.createFrom().iterable(servers)
                                    .onItem().transformToUniAndConcatenate(serverUri -> createPoolServer(serverUri,
                                            defaultPoolId,
                                            defaultPoolConfig))
                                    .collect().asList())
                            .replaceWithVoid();
                });
    }

    Uni<PoolModel> createDefaultPool() {
        final var defaultPoolId = getConfigOperation.getServiceConfig().defaults().poolId();
        final var pool = poolModelFactory.create(defaultPoolId, "bootstrap/defaultPool");
        final var request = new SyncPoolRequest(pool);
        return poolModule.getPoolService().syncPoolWithIdempotency(request)
                .replaceWith(pool);
    }

    Uni<Boolean> createPoolServer(final URI serverUri,
                                  final Long defaultPoolId,
                                  final ServiceConfig.BootstrapDefaultPoolConfig defaultPoolConfig) {
        final var poolServerConfig = PoolServerConfigDto.create();
        poolServerConfig.setServerUri(serverUri);
        poolServerConfig.setDockerHostConfig(new PoolServerConfigDto.DockerHostConfig(
                defaultPoolConfig.dockerUri(),
                defaultPoolConfig.cpuCount(),
                defaultPoolConfig.memorySize(),
                defaultPoolConfig.maxContainers()));

        final var idempotencyKey = "bootstrap/poolServer/" + serverUri;

        final var poolServer = poolServerModelFactory.create(defaultPoolId,
                PoolServerQualifierEnum.SERVICE_DOCKER_HOST,
                poolServerConfig,
                idempotencyKey);

        final var request = new SyncPoolServerRequest(poolServer);
        return poolModule.getPoolService().syncPoolServerWithIdempotency(request)
                .map(SyncPoolServerResponse::getCreated);
    }
}
