package com.omgservers.service.handler.impl.pool;

import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
import com.omgservers.schema.model.poolServer.PoolServerModel;
import com.omgservers.schema.model.poolSeverContainer.PoolContainerModel;
import com.omgservers.schema.module.docker.StartDockerContainerRequest;
import com.omgservers.schema.module.docker.StartDockerContainerResponse;
import com.omgservers.schema.module.pool.poolContainer.GetPoolContainerRequest;
import com.omgservers.schema.module.pool.poolContainer.GetPoolContainerResponse;
import com.omgservers.schema.module.pool.poolServer.GetPoolServerRequest;
import com.omgservers.schema.module.pool.poolServer.GetPoolServerResponse;
import com.omgservers.schema.module.runtime.poolContainerRef.SyncRuntimePoolContainerRefRequest;
import com.omgservers.schema.module.runtime.poolContainerRef.SyncRuntimePoolContainerRefResponse;
import com.omgservers.service.event.EventModel;
import com.omgservers.service.event.EventQualifierEnum;
import com.omgservers.service.event.body.module.pool.PoolContainerCreatedEventBodyModel;
import com.omgservers.service.exception.ServerSideBaseException;
import com.omgservers.service.exception.ServerSideConflictException;
import com.omgservers.service.factory.runtime.RuntimePoolContainerRefModelFactory;
import com.omgservers.service.handler.EventHandler;
import com.omgservers.service.module.docker.DockerModule;
import com.omgservers.service.module.pool.PoolModule;
import com.omgservers.service.module.runtime.RuntimeModule;
import com.omgservers.service.operation.getServiceConfig.GetServiceConfigOperation;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class PoolContainerCreatedEventHandlerImpl implements EventHandler {

    final RuntimeModule runtimeModule;
    final DockerModule dockerModule;
    final PoolModule poolModule;

    final GetServiceConfigOperation getServiceConfigOperation;

    final RuntimePoolContainerRefModelFactory runtimePoolContainerRefModelFactory;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.POOL_CONTAINER_CREATED;
    }

    @Override
    public Uni<Void> handle(final EventModel event) {
        log.trace("Handle event, {}", event);

        final var body = (PoolContainerCreatedEventBodyModel) event.getBody();
        final var poolId = body.getPoolId();
        final var serverId = body.getServerId();
        final var id = body.getId();

        return getPoolContainer(poolId, serverId, id)
                .flatMap(poolContainer -> {
                    log.debug("Created, {}", poolContainer);
                    return createRuntimePoolContainerRef(poolContainer)
                            .flatMap(created -> getPoolServer(poolId, serverId)
                                    .flatMap(poolServer -> startDockerContainer(poolServer, poolContainer)));
                })
                .replaceWithVoid();
    }

    Uni<PoolContainerModel> getPoolContainer(final Long poolId,
                                             final Long serverId,
                                             final Long id) {
        final var request = new GetPoolContainerRequest(poolId, serverId, id);
        return poolModule.getPoolService().execute(request)
                .map(GetPoolContainerResponse::getPoolContainer);
    }

    Uni<Boolean> createRuntimePoolContainerRef(final PoolContainerModel poolContainer) {
        final var poolId = poolContainer.getPoolId();
        final var serverId = poolContainer.getServerId();
        final var runtimeId = poolContainer.getRuntimeId();
        final var containerId = poolContainer.getId();
        final var runtimePoolContainerRef = runtimePoolContainerRefModelFactory
                .create(runtimeId, poolId, serverId, containerId);
        final var request = new SyncRuntimePoolContainerRefRequest(runtimePoolContainerRef);
        return runtimeModule.getService().execute(request)
                .map(SyncRuntimePoolContainerRefResponse::getCreated)
                .onFailure(ServerSideConflictException.class)
                .recoverWithUni(t -> {
                    if (t instanceof final ServerSideBaseException exception) {
                        if (exception.getQualifier().equals(ExceptionQualifierEnum.IDEMPOTENCY_VIOLATED)) {
                            log.debug("Idempotency was violated, object={}, {}",
                                    runtimePoolContainerRef, t.getMessage());
                            return Uni.createFrom().item(Boolean.FALSE);
                        }
                    }

                    return Uni.createFrom().failure(t);
                });
    }

    Uni<PoolServerModel> getPoolServer(final Long poolId, final Long id) {
        final var request = new GetPoolServerRequest(poolId, id);
        return poolModule.getPoolService().execute(request)
                .map(GetPoolServerResponse::getPoolServer);
    }

    Uni<Boolean> startDockerContainer(final PoolServerModel poolServer,
                                      final PoolContainerModel poolContainer) {
        final var request = new StartDockerContainerRequest(poolServer, poolContainer);
        return dockerModule.getService().execute(request)
                .map(StartDockerContainerResponse::getStarted);
    }
}
