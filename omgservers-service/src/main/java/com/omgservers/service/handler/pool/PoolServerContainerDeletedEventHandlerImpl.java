package com.omgservers.service.handler.pool;

import com.github.dockerjava.api.exception.NotFoundException;
import com.github.dockerjava.api.exception.NotModifiedException;
import com.omgservers.model.dto.pool.poolServer.GetPoolServerRequest;
import com.omgservers.model.dto.pool.poolServer.GetPoolServerResponse;
import com.omgservers.model.dto.pool.poolServerContainer.GetPoolServerContainerRequest;
import com.omgservers.model.dto.pool.poolServerContainer.GetPoolServerContainerResponse;
import com.omgservers.model.dto.runtime.poolServerContainerRef.DeleteRuntimePoolServerContainerRefRequest;
import com.omgservers.model.dto.runtime.poolServerContainerRef.DeleteRuntimePoolServerContainerRefResponse;
import com.omgservers.model.dto.runtime.poolServerContainerRef.FindRuntimePoolServerContainerRefRequest;
import com.omgservers.model.dto.runtime.poolServerContainerRef.FindRuntimePoolServerContainerRefResponse;
import com.omgservers.model.event.EventModel;
import com.omgservers.model.event.EventQualifierEnum;
import com.omgservers.model.event.body.module.pool.PoolServerContainerDeletedEventBodyModel;
import com.omgservers.model.poolServer.PoolServerModel;
import com.omgservers.model.poolSeverContainer.PoolServerContainerModel;
import com.omgservers.model.runtimePoolServerContainerRef.RuntimePoolServerContainerRefModel;
import com.omgservers.service.exception.ExceptionQualifierEnum;
import com.omgservers.service.exception.ServerSideNotFoundException;
import com.omgservers.service.handler.EventHandler;
import com.omgservers.service.handler.pool.operation.GetDockerClientOperation;
import com.omgservers.service.module.pool.PoolModule;
import com.omgservers.service.module.runtime.RuntimeModule;
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
                    final var containerName = poolServerContainer.getId().toString();
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
                        throw new ServerSideNotFoundException(ExceptionQualifierEnum.DOCKER_CONTAINER_NOT_FOUND,
                                e.getMessage(), e);
                    }
                })
                .replaceWithVoid();
    }
}
