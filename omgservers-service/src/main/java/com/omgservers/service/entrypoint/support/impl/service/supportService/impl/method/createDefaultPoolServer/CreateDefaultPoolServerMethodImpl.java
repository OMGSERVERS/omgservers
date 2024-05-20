package com.omgservers.service.entrypoint.support.impl.service.supportService.impl.method.createDefaultPoolServer;

import com.omgservers.model.dto.pool.pool.GetPoolRequest;
import com.omgservers.model.dto.pool.pool.GetPoolResponse;
import com.omgservers.model.dto.pool.poolServer.SyncPoolServerRequest;
import com.omgservers.model.dto.pool.poolServer.SyncPoolServerResponse;
import com.omgservers.model.dto.root.GetRootRequest;
import com.omgservers.model.dto.root.GetRootResponse;
import com.omgservers.model.dto.support.CreateDefaultPoolServerSupportRequest;
import com.omgservers.model.dto.support.CreateDefaultPoolServerSupportResponse;
import com.omgservers.model.pool.PoolModel;
import com.omgservers.model.poolServer.PoolServerConfigModel;
import com.omgservers.model.poolServer.PoolServerModel;
import com.omgservers.model.root.RootModel;
import com.omgservers.service.exception.ExceptionQualifierEnum;
import com.omgservers.service.exception.ServerSideBadRequestException;
import com.omgservers.service.factory.pool.PoolServerModelFactory;
import com.omgservers.service.module.pool.PoolModule;
import com.omgservers.service.module.root.RootModule;
import com.omgservers.service.operation.getConfig.GetConfigOperation;
import com.omgservers.service.operation.getDockerClient.GetDockerClientOperation;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class CreateDefaultPoolServerMethodImpl implements CreateDefaultPoolServerMethod {

    final RootModule rootModule;
    final PoolModule poolModule;

    final GetDockerClientOperation getDockerClientOperation;
    final GetConfigOperation getConfigOperation;

    final PoolServerModelFactory poolServerModelFactory;

    @Override
    public Uni<CreateDefaultPoolServerSupportResponse> createDefaultPoolServer(
            final CreateDefaultPoolServerSupportRequest request) {
        log.debug("Create default pool server, request={}", request);

        final var rootId = getConfigOperation.getServiceConfig().bootstrap().rootId();
        return getRoot(rootId)
                .flatMap(root -> {
                    final var defaultPoolId = root.getDefaultPoolId();
                    return getPool(defaultPoolId)
                            .flatMap(pool -> {
                                final var dockerDaemonUri = request.getDockerDaemonUri();
                                final var dockerClient = getDockerClientOperation.getClient(dockerDaemonUri);
                                try {
                                    dockerClient.pingCmd().exec();
                                    log.info("Docker host was checked, dockerDaemonUri={}", dockerDaemonUri);
                                } catch (Exception e) {
                                    log.error("Docker host didn't respond, dockerDaemonUri={}, {}:{}",
                                            dockerDaemonUri, e.getClass().getSimpleName(), e.getMessage());
                                    throw new ServerSideBadRequestException(
                                            ExceptionQualifierEnum.DOCKER_DAEMON_UNREACHED, e.getMessage());
                                }

                                final var poolServerConfig = PoolServerConfigModel.create();
                                final var dockerHostConfig = PoolServerConfigModel.DockerHostConfig.builder()
                                        .dockerDaemonUri(request.getDockerDaemonUri())
                                        .cpuCount(request.getCpuCount())
                                        .memorySize(request.getMemorySize())
                                        .maxContainers(request.getMaxContainers())
                                        .build();
                                poolServerConfig.setDockerHostConfig(dockerHostConfig);

                                final var qualifier = request.getQualifier();
                                final var poolServer = poolServerModelFactory.create(defaultPoolId,
                                        qualifier,
                                        poolServerConfig);

                                return syncPoolServer(poolServer);
                            });
                })
                .map(CreateDefaultPoolServerSupportResponse::new);
    }

    Uni<RootModel> getRoot(final Long id) {
        final var getRootRequest = new GetRootRequest(id);
        return rootModule.getRootService().getRoot(getRootRequest)
                .map(GetRootResponse::getRoot);
    }

    Uni<PoolModel> getPool(final Long id) {
        final var request = new GetPoolRequest(id);
        return poolModule.getPoolService().getPool(request)
                .map(GetPoolResponse::getPool);
    }

    Uni<Boolean> syncPoolServer(final PoolServerModel poolServer) {
        final var request = new SyncPoolServerRequest(poolServer);
        return poolModule.getPoolService().syncPoolServerWithIdempotency(request)
                .map(SyncPoolServerResponse::getCreated);
    }
}
