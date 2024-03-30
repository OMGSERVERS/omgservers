package com.omgservers.service.handler.server;

import com.github.dockerjava.api.exception.NotFoundException;
import com.github.dockerjava.api.exception.NotModifiedException;
import com.omgservers.model.dto.runtime.serverRuntimeRef.DeleteRuntimeServerContainerRefRequest;
import com.omgservers.model.dto.runtime.serverRuntimeRef.DeleteRuntimeServerContainerRefResponse;
import com.omgservers.model.dto.runtime.serverRuntimeRef.FindRuntimeServerContainerRefRequest;
import com.omgservers.model.dto.runtime.serverRuntimeRef.FindRuntimeServerContainerRefResponse;
import com.omgservers.model.dto.server.GetServerContainerRequest;
import com.omgservers.model.dto.server.GetServerContainerResponse;
import com.omgservers.model.dto.server.GetServerRequest;
import com.omgservers.model.dto.server.GetServerResponse;
import com.omgservers.model.event.EventModel;
import com.omgservers.model.event.EventQualifierEnum;
import com.omgservers.model.event.body.module.server.ServerContainerDeletedEventBodyModel;
import com.omgservers.model.runtimeServerContainerRef.RuntimeServerContainerRefModel;
import com.omgservers.model.server.ServerModel;
import com.omgservers.model.serverContainer.ServerContainerModel;
import com.omgservers.service.exception.ExceptionQualifierEnum;
import com.omgservers.service.exception.ServerSideNotFoundException;
import com.omgservers.service.handler.EventHandler;
import com.omgservers.service.handler.server.operation.GetDockerClientOperation;
import com.omgservers.service.module.runtime.RuntimeModule;
import com.omgservers.service.module.server.ServerModule;
import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.infrastructure.Infrastructure;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class ServerContainerDeletedEventHandlerImpl implements EventHandler {

    final RuntimeModule runtimeModule;
    final ServerModule serverModule;

    final GetDockerClientOperation getDockerClientOperation;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.SERVER_CONTAINER_DELETED;
    }

    @Override
    public Uni<Void> handle(final EventModel event) {
        log.debug("Handle event, {}", event);

        final var body = (ServerContainerDeletedEventBodyModel) event.getBody();
        final var serverId = body.getServerId();
        final var id = body.getId();

        return getServerContainer(serverId, id)
                .flatMap(serverContainer -> {
                    log.info("Server container was deleted, serverContainer={}/{}", serverId, id);

                    return findAndDeleteRuntimeServerContainerRef(serverContainer)
                            .flatMap(voidItem -> getServer(serverId)
                                    .flatMap(server -> stopServerContainer(server, serverContainer)));
                })
                .replaceWithVoid();
    }

    Uni<ServerContainerModel> getServerContainer(final Long serverId, final Long id) {
        final var request = new GetServerContainerRequest(serverId, id);
        return serverModule.getServerService().getServerContainer(request)
                .map(GetServerContainerResponse::getServerContainer);
    }

    Uni<Void> findAndDeleteRuntimeServerContainerRef(final ServerContainerModel serverContainer) {
        final var runtimeId = serverContainer.getRuntimeId();
        return findRuntimeServerContainerRef(runtimeId)
                .onFailure(ServerSideNotFoundException.class)
                .recoverWithNull()
                .onItem().ifNotNull().transformToUni(this::deleteRuntimeServerContainerRef)
                .replaceWithVoid();
    }

    Uni<RuntimeServerContainerRefModel> findRuntimeServerContainerRef(final Long runtimeId) {
        final var request = new FindRuntimeServerContainerRefRequest(runtimeId);
        return runtimeModule.getRuntimeService().findRuntimeServerContainerRef(request)
                .map(FindRuntimeServerContainerRefResponse::getRuntimeServerContainerRef);
    }

    Uni<Boolean> deleteRuntimeServerContainerRef(final RuntimeServerContainerRefModel runtimeServerContainerRef) {
        final var runtimeId = runtimeServerContainerRef.getRuntimeId();
        final var id = runtimeServerContainerRef.getId();
        final var request = new DeleteRuntimeServerContainerRefRequest(runtimeId, id);
        return runtimeModule.getRuntimeService().deleteRuntimeServerContainerRef(request)
                .map(DeleteRuntimeServerContainerRefResponse::getDeleted);
    }

    Uni<ServerModel> getServer(final Long id) {
        final var request = new GetServerRequest(id);
        return serverModule.getServerService().getServer(request)
                .map(GetServerResponse::getServer);
    }

    Uni<Void> stopServerContainer(final ServerModel server, final ServerContainerModel serverContainer) {
        return Uni.createFrom().voidItem()
                .emitOn(Infrastructure.getDefaultWorkerPool())
                .invoke(voidItem -> {
                    final var containerName = serverContainer.getId().toString();
                    final var dockerClient = getDockerClientOperation.getClient(server.getUri());

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
