package com.omgservers.service.handler.pool;

import com.github.dockerjava.api.exception.NotFoundException;
import com.github.dockerjava.api.exception.NotModifiedException;
import com.omgservers.schema.module.pool.poolServer.GetPoolServerRequest;
import com.omgservers.schema.module.pool.poolServer.GetPoolServerResponse;
import com.omgservers.schema.module.pool.poolServerContainer.GetPoolServerContainerRequest;
import com.omgservers.schema.module.pool.poolServerContainer.GetPoolServerContainerResponse;
import com.omgservers.schema.module.runtime.poolServerContainerRef.DeleteRuntimePoolServerContainerRefRequest;
import com.omgservers.schema.module.runtime.poolServerContainerRef.DeleteRuntimePoolServerContainerRefResponse;
import com.omgservers.schema.module.runtime.poolServerContainerRef.FindRuntimePoolServerContainerRefRequest;
import com.omgservers.schema.module.runtime.poolServerContainerRef.FindRuntimePoolServerContainerRefResponse;
import com.omgservers.service.event.EventModel;
import com.omgservers.service.event.EventQualifierEnum;
import com.omgservers.service.event.body.module.pool.PoolServerContainerDeletedEventBodyModel;
import com.omgservers.schema.model.poolServer.PoolServerModel;
import com.omgservers.schema.model.poolSeverContainer.PoolServerContainerModel;
import com.omgservers.schema.model.runtimePoolServerContainerRef.RuntimePoolServerContainerRefModel;
import com.omgservers.service.exception.ServerSideNotFoundException;
import com.omgservers.service.handler.EventHandler;
import com.omgservers.service.module.pool.PoolModule;
import com.omgservers.service.module.runtime.RuntimeModule;
import com.omgservers.service.operation.getDockerClient.GetDockerClientOperation;
import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.infrastructure.Infrastructure;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class PoolServerContainerDeletedEventHandlerImpl implements EventHandler {

    final RuntimeModule runtimeModule;
    final PoolModule poolModule;

    final GetDockerClientOperation getDockerClientOperation;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.POOL_SERVER_CONTAINER_DELETED;
    }

    @Override
    public Uni<Void> handle(final EventModel event) {
        log.debug("Handle event, {}", event);

        final var body = (PoolServerContainerDeletedEventBodyModel) event.getBody();
        final var poolId = body.getPoolId();
        final var serverId = body.getServerId();
        final var id = body.getId();

        return getPoolServerContainer(poolId, serverId, id)
                .flatMap(poolServerContainer -> {
                    log.info("Pool server container was deleted, id={}/{}/{}", poolId, serverId, id);

                    return findAndDeleteRuntimePoolServerContainerRef(poolServerContainer)
                            .flatMap(voidItem -> getPoolServer(poolId, serverId)
                                    .flatMap(poolServer -> stopServerContainer(poolServer, poolServerContainer)));
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

    Uni<Void> findAndDeleteRuntimePoolServerContainerRef(final PoolServerContainerModel poolServerContainer) {
        final var runtimeId = poolServerContainer.getRuntimeId();
        return findRuntimePoolServerContainerRef(runtimeId)
                .onFailure(ServerSideNotFoundException.class)
                .recoverWithNull()
                .onItem().ifNotNull().transformToUni(this::deleteRuntimePoolServerContainerRef)
                .replaceWithVoid();
    }

    Uni<RuntimePoolServerContainerRefModel> findRuntimePoolServerContainerRef(final Long runtimeId) {
        final var request = new FindRuntimePoolServerContainerRefRequest(runtimeId);
        return runtimeModule.getRuntimeService().findRuntimePoolServerContainerRef(request)
                .map(FindRuntimePoolServerContainerRefResponse::getRuntimePoolServerContainerRef);
    }

    Uni<Boolean> deleteRuntimePoolServerContainerRef(
            final RuntimePoolServerContainerRefModel runtimeServerContainerRef) {
        final var runtimeId = runtimeServerContainerRef.getRuntimeId();
        final var id = runtimeServerContainerRef.getId();
        final var request = new DeleteRuntimePoolServerContainerRefRequest(runtimeId, id);
        return runtimeModule.getRuntimeService().deleteRuntimePoolServerContainerRef(request)
                .map(DeleteRuntimePoolServerContainerRefResponse::getDeleted);
    }

    Uni<PoolServerModel> getPoolServer(final Long poolId, final Long id) {
        final var request = new GetPoolServerRequest(poolId, id);
        return poolModule.getPoolService().getPoolServer(request)
                .map(GetPoolServerResponse::getPoolServer);
    }

    Uni<Void> stopServerContainer(final PoolServerModel poolServer,
                                  final PoolServerContainerModel poolServerContainer) {
        return Uni.createFrom().voidItem()
                .emitOn(Infrastructure.getDefaultWorkerPool())
                .invoke(voidItem -> {
                    final var containerName = "pool_" + poolServerContainer.getPoolId() +
                            "_container_" + poolServerContainer.getId();
                    final var dockerDaemonUri = poolServer.getConfig().getDockerHostConfig().getDockerDaemonUri();
                    final var dockerClient = getDockerClientOperation.getClient(dockerDaemonUri);

                    try {
                        final var stopContainerResponse = dockerClient.stopContainerCmd(containerName).exec();
                        log.info("Stop container, response={}", stopContainerResponse);
                        final var removeContainerResponse = dockerClient.removeContainerCmd(containerName).exec();
                        log.info("Remove container, response={}", removeContainerResponse);
                    } catch (NotModifiedException e) {
                        log.info("Stop container failed, {}", e.getMessage());
                    } catch (NotFoundException e) {
                        log.info("Container was not found to stop, {}", e.getMessage());
                    }
                })
                .replaceWithVoid();
    }
}
