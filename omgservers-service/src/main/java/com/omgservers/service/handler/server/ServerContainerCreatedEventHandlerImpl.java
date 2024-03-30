package com.omgservers.service.handler.server;

import com.github.dockerjava.api.model.ExposedPort;
import com.github.dockerjava.api.model.HostConfig;
import com.github.dockerjava.api.model.PortBinding;
import com.omgservers.model.dto.runtime.serverRuntimeRef.SyncRuntimeServerContainerRefRequest;
import com.omgservers.model.dto.runtime.serverRuntimeRef.SyncRuntimeServerContainerRefResponse;
import com.omgservers.model.dto.server.GetServerContainerRequest;
import com.omgservers.model.dto.server.GetServerContainerResponse;
import com.omgservers.model.dto.server.GetServerRequest;
import com.omgservers.model.dto.server.GetServerResponse;
import com.omgservers.model.event.EventModel;
import com.omgservers.model.event.EventQualifierEnum;
import com.omgservers.model.event.body.module.server.ServerContainerCreatedEventBodyModel;
import com.omgservers.model.server.ServerModel;
import com.omgservers.model.serverContainer.ServerContainerModel;
import com.omgservers.service.exception.ExceptionQualifierEnum;
import com.omgservers.service.exception.ServerSideBaseException;
import com.omgservers.service.exception.ServerSideConflictException;
import com.omgservers.service.factory.RuntimeServerContainerRefModelFactory;
import com.omgservers.service.handler.EventHandler;
import com.omgservers.service.handler.server.operation.GetDockerClientOperation;
import com.omgservers.service.module.runtime.RuntimeModule;
import com.omgservers.service.module.server.ServerModule;
import com.omgservers.service.operation.getConfig.GetConfigOperation;
import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.infrastructure.Infrastructure;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class ServerContainerCreatedEventHandlerImpl implements EventHandler {

    final RuntimeModule runtimeModule;
    final ServerModule serverModule;

    final GetDockerClientOperation getDockerClientOperation;
    final GetConfigOperation getConfigOperation;

    final RuntimeServerContainerRefModelFactory runtimeServerContainerRefModelFactory;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.SERVER_CONTAINER_CREATED;
    }

    @Override
    public Uni<Void> handle(final EventModel event) {
        log.debug("Handle event, {}", event);

        final var body = (ServerContainerCreatedEventBodyModel) event.getBody();
        final var serverId = body.getServerId();
        final var id = body.getId();

        return getServerContainer(serverId, id)
                .flatMap(serverContainer -> {
                    log.info("Server container was created, serverContainer={}/{}", serverId, id);

                    return syncRuntimeServerContainerRef(serverContainer)
                            .flatMap(created -> getServer(serverId)
                                    .flatMap(server -> startServerContainer(server, serverContainer)));
                })
                .replaceWithVoid();
    }

    Uni<ServerContainerModel> getServerContainer(final Long serverId, final Long id) {
        final var request = new GetServerContainerRequest(serverId, id);
        return serverModule.getServerService().getServerContainer(request)
                .map(GetServerContainerResponse::getServerContainer);
    }

    Uni<Boolean> syncRuntimeServerContainerRef(final ServerContainerModel serverContainer) {
        final var runtimeId = serverContainer.getRuntimeId();
        final var serverId = serverContainer.getServerId();
        final var serverContainerId = serverContainer.getId();
        final var runtimeServerContainerRef = runtimeServerContainerRefModelFactory.create(runtimeId,
                serverId,
                serverContainerId);
        final var request = new SyncRuntimeServerContainerRefRequest(runtimeServerContainerRef);
        return runtimeModule.getRuntimeService().syncRuntimeServerContainerRef(request)
                .map(SyncRuntimeServerContainerRefResponse::getCreated)
                .onFailure(ServerSideConflictException.class)
                .recoverWithUni(t -> {
                    if (t instanceof final ServerSideBaseException exception) {
                        if (exception.getQualifier().equals(ExceptionQualifierEnum.IDEMPOTENCY_VIOLATION)) {
                            log.warn("Idempotency was violated, object={}, {}",
                                    runtimeServerContainerRef, t.getMessage());
                            return Uni.createFrom().item(Boolean.FALSE);
                        }
                    }

                    return Uni.createFrom().failure(t);
                });
    }

    Uni<ServerModel> getServer(final Long id) {
        final var request = new GetServerRequest(id);
        return serverModule.getServerService().getServer(request)
                .map(GetServerResponse::getServer);
    }

    Uni<Void> startServerContainer(final ServerModel server,
                                   final ServerContainerModel serverContainer) {
        return Uni.createFrom().voidItem()
                .emitOn(Infrastructure.getDefaultWorkerPool())
                .invoke(voidItem -> {
                    final var image = serverContainer.getImage();
                    final var name = serverContainer.getId().toString();
                    final var environment = serverContainer.getConfig().getEnvironment().entrySet().stream()
                            .map(entry -> entry.getKey() + "=" + entry.getValue())
                            .toList();

                    final var dockerClient = getDockerClientOperation.getClient(server.getUri());
                    final var dockerNetwork = getConfigOperation.getServiceConfig().workers().dockerNetwork();

                    final var createContainerResponse = dockerClient.createContainerCmd(image)
                            .withName(name)
                            .withEnv(environment)
                            .withExposedPorts(ExposedPort.parse("8080/tcp"))
                            .withHostConfig(HostConfig.newHostConfig()
                                    .withNetworkMode(dockerNetwork)
                                    .withPortBindings(PortBinding.parse(":8080")))
                            .exec();
                    log.info("Create docker container, response={}", createContainerResponse);

                    final var inspectContainerResponse = dockerClient.inspectContainerCmd(name)
                            .exec();
                    log.info("Inspect container, response={}", inspectContainerResponse);

                    final var startContainerResponse = dockerClient.startContainerCmd(name)
                            .exec();

                    log.info("Start container, response={}", startContainerResponse);
                });
    }
}
