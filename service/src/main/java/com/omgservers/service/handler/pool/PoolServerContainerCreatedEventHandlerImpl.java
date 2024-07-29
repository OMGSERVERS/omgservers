package com.omgservers.service.handler.pool;

import com.github.dockerjava.api.model.HostConfig;
import com.github.dockerjava.api.model.LogConfig;
import com.github.dockerjava.api.model.RestartPolicy;
import com.omgservers.schema.module.pool.poolServer.GetPoolServerRequest;
import com.omgservers.schema.module.pool.poolServer.GetPoolServerResponse;
import com.omgservers.schema.module.pool.poolServerContainer.GetPoolServerContainerRequest;
import com.omgservers.schema.module.pool.poolServerContainer.GetPoolServerContainerResponse;
import com.omgservers.schema.module.runtime.poolServerContainerRef.SyncRuntimePoolServerContainerRefRequest;
import com.omgservers.schema.module.runtime.poolServerContainerRef.SyncRuntimePoolServerContainerRefResponse;
import com.omgservers.schema.event.EventModel;
import com.omgservers.schema.event.EventQualifierEnum;
import com.omgservers.schema.event.body.module.pool.PoolServerContainerCreatedEventBodyModel;
import com.omgservers.schema.model.poolServer.PoolServerModel;
import com.omgservers.schema.model.poolSeverContainer.PoolServerContainerModel;
import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
import com.omgservers.service.exception.ServerSideBaseException;
import com.omgservers.service.exception.ServerSideConflictException;
import com.omgservers.service.factory.runtime.RuntimePoolServerContainerRefModelFactory;
import com.omgservers.service.handler.EventHandler;
import com.omgservers.service.module.pool.PoolModule;
import com.omgservers.service.module.runtime.RuntimeModule;
import com.omgservers.service.operation.getConfig.GetConfigOperation;
import com.omgservers.service.operation.getDockerClient.GetDockerClientOperation;
import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.infrastructure.Infrastructure;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class PoolServerContainerCreatedEventHandlerImpl implements EventHandler {

    final PoolModule poolModule;
    final RuntimeModule runtimeModule;

    final GetDockerClientOperation getDockerClientOperation;
    final GetConfigOperation getConfigOperation;

    final RuntimePoolServerContainerRefModelFactory runtimePoolServerContainerRefModelFactory;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.POOL_SERVER_CONTAINER_CREATED;
    }

    @Override
    public Uni<Void> handle(final EventModel event) {
        log.debug("Handle event, {}", event);

        final var body = (PoolServerContainerCreatedEventBodyModel) event.getBody();
        final var poolId = body.getPoolId();
        final var serverId = body.getServerId();
        final var id = body.getId();

        return getPoolServerContainer(poolId, serverId, id)
                .flatMap(poolServerContainer -> {
                    final var imageId = poolServerContainer.getConfig().getImageId();
                    log.info("Pool server container was created, id={}/{}/{}, image={}", poolId, serverId, id, imageId);

                    return syncRuntimePoolServerContainerRef(poolServerContainer)
                            .flatMap(created -> getPoolServer(poolId, serverId)
                                    .flatMap(poolServer -> startPoolServerContainer(poolServer, poolServerContainer)));
                })
                .replaceWithVoid();
    }

    Uni<PoolServerContainerModel> getPoolServerContainer(final Long poolId,
                                                         final Long serverId,
                                                         final Long id) {
        final var request = new GetPoolServerContainerRequest(poolId, serverId, id);
        return poolModule.getPoolService().getPoolServerContainer(request)
                .map(GetPoolServerContainerResponse::getPoolServerContainer);
    }

    Uni<Boolean> syncRuntimePoolServerContainerRef(final PoolServerContainerModel poolServerContainer) {
        final var poolId = poolServerContainer.getPoolId();
        final var serverId = poolServerContainer.getServerId();
        final var runtimeId = poolServerContainer.getRuntimeId();
        final var containerId = poolServerContainer.getId();
        final var runtimeServerContainerRef = runtimePoolServerContainerRefModelFactory.create(runtimeId,
                poolId,
                serverId,
                containerId);
        final var request = new SyncRuntimePoolServerContainerRefRequest(runtimeServerContainerRef);
        return runtimeModule.getRuntimeService().syncRuntimePoolServerContainerRef(request)
                .map(SyncRuntimePoolServerContainerRefResponse::getCreated)
                .onFailure(ServerSideConflictException.class)
                .recoverWithUni(t -> {
                    if (t instanceof final ServerSideBaseException exception) {
                        if (exception.getQualifier().equals(ExceptionQualifierEnum.IDEMPOTENCY_VIOLATED)) {
                            log.warn("Idempotency was violated, object={}, {}",
                                    runtimeServerContainerRef, t.getMessage());
                            return Uni.createFrom().item(Boolean.FALSE);
                        }
                    }

                    return Uni.createFrom().failure(t);
                });
    }

    Uni<PoolServerModel> getPoolServer(final Long poolId, final Long id) {
        final var request = new GetPoolServerRequest(poolId, id);
        return poolModule.getPoolService().getPoolServer(request)
                .map(GetPoolServerResponse::getPoolServer);
    }

    Uni<Void> startPoolServerContainer(final PoolServerModel poolServerModel,
                                       final PoolServerContainerModel poolServerContainer) {
        return Uni.createFrom().voidItem()
                .emitOn(Infrastructure.getDefaultWorkerPool())
                .invoke(voidItem -> {
                    final var imageId = poolServerContainer.getConfig().getImageId();
                    final var containerName = "pool_" + poolServerContainer.getPoolId() +
                            "_container_" + poolServerContainer.getId();
                    final var environment = poolServerContainer.getConfig().getEnvironment().entrySet().stream()
                            .map(entry -> entry.getKey() + "=" + entry.getValue())
                            .toList();

                    final var dockerDaemonUri = poolServerModel.getConfig().getDockerHostConfig().getDockerDaemonUri();
                    final var dockerClient = getDockerClientOperation.getClient(dockerDaemonUri);
                    final var dockerNetwork = getConfigOperation.getServiceConfig().workers().dockerNetwork();

                    try {
                        // Convert milliseconds -> microseconds
                        final var cpuQuotaInMicroseconds = poolServerContainer.getConfig()
                                .getCpuLimitInMilliseconds() * 1000L;
                        // Convert megabytes -> bytes
                        final var memoryLimitInBytes = poolServerContainer.getConfig()
                                .getMemoryLimitInMegabytes() * 1024L * 1024L;

                        final var logConfig = new LogConfig();
                        logConfig.setType(LogConfig.LoggingType.JSON_FILE);
                        logConfig.setConfig(Map.of(
                                "max-size", "10m",
                                "max-file", "8"
                        ));

                        final var hostConfig = HostConfig.newHostConfig()
                                .withLogConfig(logConfig)
                                .withNetworkMode(dockerNetwork)
                                .withCpuQuota(cpuQuotaInMicroseconds)
                                .withMemory(memoryLimitInBytes)
                                .withRestartPolicy(RestartPolicy.unlessStoppedRestart());

                        final var createContainerResponse = dockerClient.createContainerCmd(imageId)
                                .withName(containerName)
                                .withEnv(environment)
                                .withHostConfig(hostConfig)
                                .exec();
                        log.info("Docker container was created, " +
                                        "containerName={}, dockerNetwork={}, cpuQuota={}, memoryLimit={}, response={}",
                                containerName,
                                dockerNetwork,
                                cpuQuotaInMicroseconds,
                                memoryLimitInBytes,
                                createContainerResponse);

                        final var inspectContainerResponse = dockerClient.inspectContainerCmd(containerName)
                                .exec();
                        log.info("Docker container was inspected, response={}", inspectContainerResponse);

                        final var startContainerResponse = dockerClient.startContainerCmd(containerName)
                                .exec();

                        log.info("Docker container was started, response={}", startContainerResponse);
                    } catch (Exception e) {
                        // TODO: handle docker exception
                        log.error("Start container failed, {}:{}", e.getClass().getSimpleName(), e.getMessage());
                    }
                });
    }
}
