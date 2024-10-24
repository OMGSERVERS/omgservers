package com.omgservers.service.service.bootstrap.impl.method.bootstrapDockerHost;

import com.omgservers.schema.model.pool.PoolModel;
import com.omgservers.schema.model.poolServer.PoolServerConfigDto;
import com.omgservers.schema.model.poolServer.PoolServerQualifierEnum;
import com.omgservers.schema.module.pool.pool.GetPoolRequest;
import com.omgservers.schema.module.pool.pool.GetPoolResponse;
import com.omgservers.schema.module.pool.poolServer.SyncPoolServerRequest;
import com.omgservers.schema.module.pool.poolServer.SyncPoolServerResponse;
import com.omgservers.service.factory.pool.PoolModelFactory;
import com.omgservers.service.factory.pool.PoolServerModelFactory;
import com.omgservers.service.module.pool.PoolModule;
import com.omgservers.service.operation.getConfig.GetConfigOperation;
import com.omgservers.service.operation.getConfig.ServiceConfig;
import com.omgservers.service.operation.getServers.GetServersOperation;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class BootstrapDockerHostMethodImpl implements BootstrapDockerHostMethod {

    final PoolModule poolModule;

    final GetServersOperation getServersOperation;
    final GetConfigOperation getConfigOperation;

    final PoolServerModelFactory poolServerModelFactory;
    final PoolModelFactory poolModelFactory;

    @Override
    public Uni<Void> execute() {
        log.debug("Bootstrap docker host");

        final var defaultPoolId = getConfigOperation.getServiceConfig().defaults().poolId();

        return getPool(defaultPoolId)
                .flatMap(defaultPool -> {

                    final var dockerHostConfig = getConfigOperation
                            .getServiceConfig().bootstrap().dockerHost();

                    return createPoolServer(defaultPoolId, dockerHostConfig)
                            .replaceWithVoid();
                });
    }

    Uni<PoolModel> getPool(final Long defaultPoolId) {
        final var request = new GetPoolRequest(defaultPoolId);
        return poolModule.getPoolService().execute(request)
                .map(GetPoolResponse::getPool);
    }

    Uni<Boolean> createPoolServer(final Long defaultPoolId,
                                  final ServiceConfig.BootstrapDockerHostConfig dockerHostConfig) {
        final var serverUri = getConfigOperation.getServiceConfig().index().serverUri();

        final var poolServerConfig = PoolServerConfigDto.create();
        poolServerConfig.setServerUri(serverUri);
        poolServerConfig.setServiceUri(dockerHostConfig.serviceUri());
        poolServerConfig.setDockerHostConfig(new PoolServerConfigDto.DockerHostConfig(
                dockerHostConfig.dockerDaemonUri(),
                dockerHostConfig.cpuCount(),
                dockerHostConfig.memorySize(),
                dockerHostConfig.maxContainers()));

        final var idempotencyKey = "bootstrap/poolServer/" + serverUri;

        final var poolServer = poolServerModelFactory.create(defaultPoolId,
                PoolServerQualifierEnum.DOCKER_HOST,
                poolServerConfig,
                idempotencyKey);

        final var request = new SyncPoolServerRequest(poolServer);
        return poolModule.getPoolService().executeWithIdempotency(request)
                .map(SyncPoolServerResponse::getCreated);
    }
}
