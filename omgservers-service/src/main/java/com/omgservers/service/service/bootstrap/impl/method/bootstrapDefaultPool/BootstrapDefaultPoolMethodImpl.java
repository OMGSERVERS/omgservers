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

import java.util.concurrent.atomic.AtomicInteger;

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
    public Uni<Void> execute() {
        log.debug("Bootstrap default pool");

        return createDefaultPool()
                .invoke(defaultPool -> log.debug("Default pool {} was created", defaultPool.getId()))
                .flatMap(defaultPool -> {
                    final var dockerHosts = getConfigOperation.getServiceConfig()
                            .bootstrap().defaultPool().dockerHosts();

                    final var defaultPoolId = defaultPool.getId();
                    final var serverCounter = new AtomicInteger();
                    return Multi.createFrom().iterable(dockerHosts)
                            .onItem().transformToUniAndConcatenate(dockerHostConfig ->
                                    createPoolServer(defaultPoolId,
                                            serverCounter.getAndIncrement(),
                                            dockerHostConfig))
                            .collect().asList();
                })
                .replaceWithVoid();
    }

    Uni<PoolModel> createDefaultPool() {
        final var defaultPoolId = getConfigOperation.getServiceConfig().defaults().poolId();
        final var pool = poolModelFactory.create(defaultPoolId, "bootstrap/defaultPool");
        final var request = new SyncPoolRequest(pool);
        return poolModule.getPoolService().executeWithIdempotency(request)
                .replaceWith(pool);
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

        final var idempotencyKey = "bootstrap/poolServer/" + serverIndex;

        final var poolServer = poolServerModelFactory.create(defaultPoolId,
                PoolServerQualifierEnum.DOCKER_HOST,
                poolServerConfig,
                idempotencyKey);

        final var request = new SyncPoolServerRequest(poolServer);
        return poolModule.getPoolService().executeWithIdempotency(request)
                .map(SyncPoolServerResponse::getCreated)
                .invoke(created -> {
                    if (created) {
                        log.debug("Pool server {} of pool {} was created",
                                poolServer.getPoolId(), poolServer.getPoolId());
                    }
                });
    }
}
