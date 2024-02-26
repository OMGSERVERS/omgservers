package com.omgservers.service.handler.runtime;

import com.omgservers.model.container.ContainerConfigModel;
import com.omgservers.model.container.ContainerModel;
import com.omgservers.model.container.ContainerQualifierEnum;
import com.omgservers.model.dto.lobby.SyncLobbyRuntimeRefRequest;
import com.omgservers.model.dto.lobby.SyncLobbyRuntimeResponse;
import com.omgservers.model.dto.matchmaker.SyncMatchRuntimeRefRequest;
import com.omgservers.model.dto.matchmaker.SyncMatchRuntimeRefResponse;
import com.omgservers.model.dto.runtime.GetRuntimeRequest;
import com.omgservers.model.dto.runtime.GetRuntimeResponse;
import com.omgservers.model.dto.runtime.SyncRuntimePermissionRequest;
import com.omgservers.model.dto.runtime.SyncRuntimePermissionResponse;
import com.omgservers.model.dto.system.SyncContainerRequest;
import com.omgservers.model.dto.system.SyncContainerResponse;
import com.omgservers.model.dto.system.SyncEventRequest;
import com.omgservers.model.dto.system.SyncEventResponse;
import com.omgservers.model.dto.user.SyncUserRequest;
import com.omgservers.model.dto.user.SyncUserResponse;
import com.omgservers.model.event.EventModel;
import com.omgservers.model.event.EventQualifierEnum;
import com.omgservers.model.event.body.RuntimeCreatedEventBodyModel;
import com.omgservers.model.event.body.RuntimeJobTaskExecutionRequestedEventBodyModel;
import com.omgservers.model.runtime.RuntimeModel;
import com.omgservers.model.runtimePermission.RuntimePermissionEnum;
import com.omgservers.model.runtimePermission.RuntimePermissionModel;
import com.omgservers.model.user.UserModel;
import com.omgservers.model.user.UserRoleEnum;
import com.omgservers.service.exception.ServerSideNotFoundException;
import com.omgservers.service.factory.ContainerModelFactory;
import com.omgservers.service.factory.EventModelFactory;
import com.omgservers.service.factory.LobbyRuntimeRefModelFactory;
import com.omgservers.service.factory.MatchRuntimeRefModelFactory;
import com.omgservers.service.factory.RuntimePermissionModelFactory;
import com.omgservers.service.factory.UserModelFactory;
import com.omgservers.service.handler.EventHandler;
import com.omgservers.service.module.lobby.LobbyModule;
import com.omgservers.service.module.matchmaker.MatchmakerModule;
import com.omgservers.service.module.runtime.RuntimeModule;
import com.omgservers.service.module.system.SystemModule;
import com.omgservers.service.module.user.UserModule;
import com.omgservers.service.operation.getConfig.GetConfigOperation;
import io.quarkus.elytron.security.common.BcryptUtil;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.security.SecureRandom;
import java.util.HashMap;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class RuntimeCreatedEventHandlerImpl implements EventHandler {

    final MatchmakerModule matchmakerModule;
    final RuntimeModule runtimeModule;
    final SystemModule systemModule;
    final LobbyModule lobbyModule;
    final UserModule userModule;

    final GetConfigOperation getConfigOperation;

    final RuntimePermissionModelFactory runtimePermissionModelFactory;
    final LobbyRuntimeRefModelFactory lobbyRuntimeRefModelFactory;
    final MatchRuntimeRefModelFactory matchRuntimeRefModelFactory;
    final ContainerModelFactory containerModelFactory;
    final EventModelFactory eventModelFactory;
    final UserModelFactory userModelFactory;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.RUNTIME_CREATED;
    }

    @Override
    public Uni<Void> handle(final EventModel event) {
        log.debug("Handle event, {}", event);

        final var body = (RuntimeCreatedEventBodyModel) event.getBody();
        final var runtimeId = body.getId();

        return getRuntime(runtimeId)
                .flatMap(runtime -> {
                    log.info("Runtime was created, id={}, type={}",
                            runtime.getId(),
                            runtime.getQualifier());

                    return requestJobExecution(runtimeId)
                            .flatMap(created -> createContainer(runtime))
                            .flatMap(created -> syncRuntimeRef(runtime));
                })
                .replaceWithVoid();
    }

    Uni<RuntimeModel> getRuntime(final Long runtimeId) {
        final var request = new GetRuntimeRequest(runtimeId);
        return runtimeModule.getRuntimeService().getRuntime(request)
                .map(GetRuntimeResponse::getRuntime);
    }

    Uni<Boolean> requestJobExecution(final Long runtimeId) {
        final var eventBody = new RuntimeJobTaskExecutionRequestedEventBodyModel(runtimeId);
        final var eventModel = eventModelFactory.create(eventBody);

        final var syncEventRequest = new SyncEventRequest(eventModel);
        return systemModule.getEventService().syncEvent(syncEventRequest)
                .map(SyncEventResponse::getCreated);
    }

    Uni<Void> createContainer(final RuntimeModel runtime) {
        // TODO: improve it
        final var password = String.valueOf(new SecureRandom().nextLong());
        return createUser(password)
                .flatMap(user -> {
                    final var runtimeId = runtime.getId();
                    final var userId = user.getId();
                    return createRuntimePermission(runtimeId, userId)
                            .flatMap(wasRuntimePermissionCreated -> syncContainer(userId, password, runtime)
                                    .replaceWithVoid());
                });
    }

    Uni<UserModel> createUser(final String password) {
        final var passwordHash = BcryptUtil.bcryptHash(password);
        final var user = userModelFactory.create(UserRoleEnum.WORKER, passwordHash);
        final var request = new SyncUserRequest(user);
        return userModule.getUserService().syncUser(request)
                .map(SyncUserResponse::getCreated)
                .replaceWith(user);
    }

    Uni<Boolean> createRuntimePermission(final Long runtimeId, final Long userId) {
        final var runtimePermission = runtimePermissionModelFactory.create(runtimeId,
                userId,
                RuntimePermissionEnum.HANDLE_RUNTIME);
        return syncRuntimePermission(runtimePermission);
    }

    Uni<Boolean> syncRuntimePermission(final RuntimePermissionModel runtimePermission) {
        final var request = new SyncRuntimePermissionRequest(runtimePermission);
        return runtimeModule.getRuntimeService().syncRuntimePermission(request)
                .map(SyncRuntimePermissionResponse::getCreated);
    }

    Uni<Boolean> syncContainer(final Long userId,
                               final String password,
                               final RuntimeModel runtime) {
        final var runtimeId = runtime.getId();
        final var workerImage = getConfigOperation.getServiceConfig().workersImage();
        final var internalUri = getConfigOperation.getServiceConfig().internalUri();
        final var environment = new HashMap<String, String>();
        environment.put("OMGSERVERS_URL", internalUri.toString());
        environment.put("OMGSERVERS_USER_ID", userId.toString());
        environment.put("OMGSERVERS_PASSWORD", password);
        environment.put("OMGSERVERS_RUNTIME_ID", runtimeId.toString());
        environment.put("OMGSERVERS_RUNTIME_QUALIFIER", runtime.getQualifier().toString());
        final var config = new ContainerConfigModel(environment);
        final var container = containerModelFactory.create(runtimeId,
                ContainerQualifierEnum.RUNTIME,
                workerImage,
                config);
        return syncContainer(container);
    }

    Uni<Boolean> syncContainer(final ContainerModel container) {
        final var request = new SyncContainerRequest(container);
        return systemModule.getContainerService().syncContainer(request)
                .map(SyncContainerResponse::getCreated);
    }

    Uni<Boolean> syncRuntimeRef(final RuntimeModel runtime) {
        final var runtimeId = runtime.getId();
        return switch (runtime.getQualifier()) {
            case LOBBY -> {
                final var lobbyId = runtime.getConfig().getLobbyConfig().getLobbyId();
                final var lobbyRuntimeRef = lobbyRuntimeRefModelFactory.create(lobbyId, runtimeId);
                final var request = new SyncLobbyRuntimeRefRequest(lobbyRuntimeRef);
                yield lobbyModule.getLobbyService().syncLobbyRuntimeRef(request)
                        .map(SyncLobbyRuntimeResponse::getCreated)
                        .onFailure(ServerSideNotFoundException.class)
                        .recoverWithItem(Boolean.FALSE);
            }
            case MATCH -> {
                final var matchConfig = runtime.getConfig().getMatchConfig();
                final var matchmakerId = matchConfig.getMatchmakerId();
                final var matchId = matchConfig.getMatchId();
                final var matchRuntimeRef = matchRuntimeRefModelFactory.create(matchmakerId, matchId, runtimeId);
                final var request = new SyncMatchRuntimeRefRequest(matchRuntimeRef);
                yield matchmakerModule.getMatchmakerService().syncMatchRuntimeRef(request)
                        .map(SyncMatchRuntimeRefResponse::getCreated)
                        .onFailure(ServerSideNotFoundException.class)
                        .recoverWithItem(Boolean.FALSE);
            }
        };
    }
}
