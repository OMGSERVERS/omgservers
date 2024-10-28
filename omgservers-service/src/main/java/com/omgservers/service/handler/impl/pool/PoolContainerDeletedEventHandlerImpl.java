package com.omgservers.service.handler.impl.pool;

import com.omgservers.schema.model.poolServer.PoolServerModel;
import com.omgservers.schema.model.poolSeverContainer.PoolContainerModel;
import com.omgservers.schema.model.runtimePoolContainerRef.RuntimePoolContainerRefModel;
import com.omgservers.schema.module.docker.StopDockerContainerRequest;
import com.omgservers.schema.module.docker.StopDockerContainerResponse;
import com.omgservers.schema.module.pool.poolContainer.GetPoolContainerRequest;
import com.omgservers.schema.module.pool.poolContainer.GetPoolContainerResponse;
import com.omgservers.schema.module.pool.poolServer.GetPoolServerRequest;
import com.omgservers.schema.module.pool.poolServer.GetPoolServerResponse;
import com.omgservers.schema.module.runtime.poolContainerRef.DeleteRuntimePoolContainerRefRequest;
import com.omgservers.schema.module.runtime.poolContainerRef.DeleteRuntimePoolContainerRefResponse;
import com.omgservers.schema.module.runtime.poolContainerRef.FindRuntimePoolContainerRefRequest;
import com.omgservers.schema.module.runtime.poolContainerRef.FindRuntimePoolContainerRefResponse;
import com.omgservers.service.event.EventModel;
import com.omgservers.service.event.EventQualifierEnum;
import com.omgservers.service.event.body.module.pool.PoolContainerDeletedEventBodyModel;
import com.omgservers.service.exception.ServerSideNotFoundException;
import com.omgservers.service.handler.EventHandler;
import com.omgservers.service.module.docker.DockerModule;
import com.omgservers.service.module.pool.PoolModule;
import com.omgservers.service.module.runtime.RuntimeModule;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class PoolContainerDeletedEventHandlerImpl implements EventHandler {

    final RuntimeModule runtimeModule;
    final DockerModule dockerModule;
    final PoolModule poolModule;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.POOL_CONTAINER_DELETED;
    }

    @Override
    public Uni<Void> handle(final EventModel event) {
        log.debug("Handle event, {}", event);

        final var body = (PoolContainerDeletedEventBodyModel) event.getBody();
        final var poolId = body.getPoolId();
        final var serverId = body.getServerId();
        final var id = body.getId();

        return getPoolContainer(poolId, serverId, id)
                .flatMap(poolContainer -> {
                    log.info("Deleted, {}", poolContainer);

                    return findAndDeleteRuntimePoolContainerRef(poolContainer)
                            .flatMap(voidItem -> getPoolServer(poolId, serverId)
                                    .flatMap(poolServer -> stopDockerContainer(poolServer, poolContainer)));
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

    Uni<Void> findAndDeleteRuntimePoolContainerRef(final PoolContainerModel poolContainer) {
        final var runtimeId = poolContainer.getRuntimeId();
        return findRuntimePoolContainerRef(runtimeId)
                .onFailure(ServerSideNotFoundException.class)
                .recoverWithNull()
                .onItem().ifNotNull().transformToUni(this::deleteRuntimePoolContainerRef)
                .replaceWithVoid();
    }

    Uni<RuntimePoolContainerRefModel> findRuntimePoolContainerRef(final Long runtimeId) {
        final var request = new FindRuntimePoolContainerRefRequest(runtimeId);
        return runtimeModule.getService().execute(request)
                .map(FindRuntimePoolContainerRefResponse::getRuntimePoolContainerRef);
    }

    Uni<Boolean> deleteRuntimePoolContainerRef(
            final RuntimePoolContainerRefModel runtimeServerContainerRef) {
        final var runtimeId = runtimeServerContainerRef.getRuntimeId();
        final var id = runtimeServerContainerRef.getId();
        final var request = new DeleteRuntimePoolContainerRefRequest(runtimeId, id);
        return runtimeModule.getService().execute(request)
                .map(DeleteRuntimePoolContainerRefResponse::getDeleted);
    }

    Uni<PoolServerModel> getPoolServer(final Long poolId, final Long id) {
        final var request = new GetPoolServerRequest(poolId, id);
        return poolModule.getPoolService().execute(request)
                .map(GetPoolServerResponse::getPoolServer);
    }

    Uni<Boolean> stopDockerContainer(final PoolServerModel poolServer,
                                     final PoolContainerModel poolContainer) {
        final var request = new StopDockerContainerRequest(poolServer, poolContainer);
        return dockerModule.getDockerService().execute(request)
                .map(StopDockerContainerResponse::getStopped);
    }
}
