package com.omgservers.service.handler.impl.pool;

import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
import com.omgservers.schema.model.poolServer.PoolServerModel;
import com.omgservers.schema.model.poolSeverContainer.PoolServerContainerModel;
import com.omgservers.schema.module.docker.StartDockerContainerRequest;
import com.omgservers.schema.module.docker.StartDockerContainerResponse;
import com.omgservers.schema.module.pool.poolServer.GetPoolServerRequest;
import com.omgservers.schema.module.pool.poolServer.GetPoolServerResponse;
import com.omgservers.schema.module.pool.poolServerContainer.GetPoolServerContainerRequest;
import com.omgservers.schema.module.pool.poolServerContainer.GetPoolServerContainerResponse;
import com.omgservers.schema.module.runtime.poolServerContainerRef.SyncRuntimePoolServerContainerRefRequest;
import com.omgservers.schema.module.runtime.poolServerContainerRef.SyncRuntimePoolServerContainerRefResponse;
import com.omgservers.service.event.EventModel;
import com.omgservers.service.event.EventQualifierEnum;
import com.omgservers.service.event.body.module.pool.PoolServerContainerCreatedEventBodyModel;
import com.omgservers.service.exception.ServerSideBaseException;
import com.omgservers.service.exception.ServerSideConflictException;
import com.omgservers.service.factory.runtime.RuntimePoolServerContainerRefModelFactory;
import com.omgservers.service.handler.EventHandler;
import com.omgservers.service.module.docker.DockerModule;
import com.omgservers.service.module.docker.impl.operation.GetDockerDaemonClientOperation;
import com.omgservers.service.module.pool.PoolModule;
import com.omgservers.service.module.runtime.RuntimeModule;
import com.omgservers.service.operation.getConfig.GetConfigOperation;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class PoolServerContainerCreatedEventHandlerImpl implements EventHandler {

    final RuntimeModule runtimeModule;
    final DockerModule dockerModule;
    final PoolModule poolModule;

    final GetDockerDaemonClientOperation getDockerDaemonClientOperation;
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
                                    .flatMap(poolServer -> startDockerContainer(poolServer, poolServerContainer)));
                })
                .replaceWithVoid();
    }

    Uni<PoolServerContainerModel> getPoolServerContainer(final Long poolId,
                                                         final Long serverId,
                                                         final Long id) {
        final var request = new GetPoolServerContainerRequest(poolId, serverId, id);
        return poolModule.getService().getPoolServerContainer(request)
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
        return runtimeModule.getService().syncRuntimePoolServerContainerRef(request)
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
        return poolModule.getService().getPoolServer(request)
                .map(GetPoolServerResponse::getPoolServer);
    }

    Uni<Boolean> startDockerContainer(final PoolServerModel poolServer,
                                      final PoolServerContainerModel poolServerContainer) {
        final var request = new StartDockerContainerRequest(poolServer, poolServerContainer);
        return dockerModule.getService().startDockerContainer(request)
                .map(StartDockerContainerResponse::getStarted);
    }
}
