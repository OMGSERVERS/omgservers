package com.omgservers.service.module.system.impl.service.bootstrapService.impl.method.bootstrapDockerHost;

import com.omgservers.model.dto.pool.poolServer.SyncPoolServerRequest;
import com.omgservers.model.dto.pool.poolServer.SyncPoolServerResponse;
import com.omgservers.model.poolServer.PoolServerConfigModel;
import com.omgservers.model.poolServer.PoolServerModel;
import com.omgservers.model.poolServer.PoolServerQualifierEnum;
import com.omgservers.service.factory.pool.PoolServerModelFactory;
import com.omgservers.service.module.pool.PoolModule;
import com.omgservers.service.operation.getConfig.GetConfigOperation;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class BootstrapDockerHostMethodImpl implements BootstrapDockerHostMethod {

    final PoolModule poolModule;

    final GetConfigOperation getConfigOperation;

    final PoolServerModelFactory poolServerModelFactory;

    @Override
    public Uni<Void> bootstrapDockerHost() {
        log.debug("Bootstrap docker host");

        final var dockerHostConfig = getConfigOperation.getServiceConfig().bootstrap().dockerHost();

        final var serverConfig = PoolServerConfigModel.create();
        serverConfig.setDockerHostConfig(new PoolServerConfigModel.DockerHostConfig(
                dockerHostConfig.uri(),
                dockerHostConfig.cpuCount(),
                dockerHostConfig.memorySize(),
                dockerHostConfig.maxContainers()));

        final var defaultPoolId = getConfigOperation.getServiceConfig().defaults().poolId();
        final var idempotencyKey = "bootstrap";

        final var poolServer = poolServerModelFactory.create(defaultPoolId,
                PoolServerQualifierEnum.DOCKER_HOST,
                serverConfig,
                idempotencyKey);

        return syncPoolServer(poolServer)
                .replaceWithVoid();
    }

    Uni<Boolean> syncPoolServer(final PoolServerModel poolServer) {
        final var request = new SyncPoolServerRequest(poolServer);
        return poolModule.getPoolService().syncPoolServerWithIdempotency(request)
                .map(SyncPoolServerResponse::getCreated);
    }
}
