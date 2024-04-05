package com.omgservers.service.handler.runtime;

import com.omgservers.model.dto.lobby.SyncLobbyRuntimeRefRequest;
import com.omgservers.model.dto.lobby.SyncLobbyRuntimeRefResponse;
import com.omgservers.model.dto.matchmaker.SyncMatchmakerMatchRuntimeRefRequest;
import com.omgservers.model.dto.matchmaker.SyncMatchmakerMatchRuntimeRefResponse;
import com.omgservers.model.dto.pool.poolRequest.SyncPoolRequestRequest;
import com.omgservers.model.dto.pool.poolRequest.SyncPoolRequestResponse;
import com.omgservers.model.dto.root.GetRootRequest;
import com.omgservers.model.dto.root.GetRootResponse;
import com.omgservers.model.dto.runtime.GetRuntimeRequest;
import com.omgservers.model.dto.runtime.GetRuntimeResponse;
import com.omgservers.model.dto.system.SyncEventRequest;
import com.omgservers.model.dto.system.SyncEventResponse;
import com.omgservers.model.dto.user.GetUserRequest;
import com.omgservers.model.dto.user.GetUserResponse;
import com.omgservers.model.dto.user.SyncUserRequest;
import com.omgservers.model.dto.user.SyncUserResponse;
import com.omgservers.model.event.EventModel;
import com.omgservers.model.event.EventQualifierEnum;
import com.omgservers.model.event.body.job.RuntimeJobTaskExecutionRequestedEventBodyModel;
import com.omgservers.model.event.body.module.runtime.RuntimeCreatedEventBodyModel;
import com.omgservers.model.poolRequest.PoolRequestConfigModel;
import com.omgservers.model.root.RootModel;
import com.omgservers.model.runtime.RuntimeModel;
import com.omgservers.model.user.UserModel;
import com.omgservers.model.user.UserRoleEnum;
import com.omgservers.service.exception.ExceptionQualifierEnum;
import com.omgservers.service.exception.ServerSideBaseException;
import com.omgservers.service.exception.ServerSideConflictException;
import com.omgservers.service.exception.ServerSideNotFoundException;
import com.omgservers.service.factory.lobby.LobbyRuntimeRefModelFactory;
import com.omgservers.service.factory.matchmaker.MatchmakerMatchRuntimeRefModelFactory;
import com.omgservers.service.factory.pool.PoolRequestModelFactory;
import com.omgservers.service.factory.runtime.RuntimePermissionModelFactory;
import com.omgservers.service.factory.system.EventModelFactory;
import com.omgservers.service.factory.user.UserModelFactory;
import com.omgservers.service.handler.EventHandler;
import com.omgservers.service.module.lobby.LobbyModule;
import com.omgservers.service.module.matchmaker.MatchmakerModule;
import com.omgservers.service.module.pool.PoolModule;
import com.omgservers.service.module.root.RootModule;
import com.omgservers.service.module.runtime.RuntimeModule;
import com.omgservers.service.module.system.SystemModule;
import com.omgservers.service.module.user.UserModule;
import com.omgservers.service.operation.generateSecureString.GenerateSecureStringOperation;
import com.omgservers.service.operation.getConfig.GetConfigOperation;
import io.quarkus.elytron.security.common.BcryptUtil;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

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
    final RootModule rootModule;
    final PoolModule poolModule;

    final GenerateSecureStringOperation generateSecureStringOperation;
    final GetConfigOperation getConfigOperation;

    final MatchmakerMatchRuntimeRefModelFactory matchmakerMatchRuntimeRefModelFactory;
    final RuntimePermissionModelFactory runtimePermissionModelFactory;
    final LobbyRuntimeRefModelFactory lobbyRuntimeRefModelFactory;
    final PoolRequestModelFactory poolRequestModelFactory;
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

                    final var idempotencyKey = event.getIdempotencyKey();

                    final var userId = runtime.getUserId();
                    final var password = generateSecureStringOperation.generateSecureString();
                    return createUser(userId, password, idempotencyKey)
                            .flatMap(user -> syncPoolRequest(runtime, user, password))
                            .flatMap(created -> syncRuntimeRef(runtime, idempotencyKey))
                            .flatMap(created -> requestJobExecution(runtimeId, idempotencyKey));
                })
                .replaceWithVoid();
    }

    Uni<RuntimeModel> getRuntime(final Long runtimeId) {
        final var request = new GetRuntimeRequest(runtimeId);
        return runtimeModule.getRuntimeService().getRuntime(request)
                .map(GetRuntimeResponse::getRuntime);
    }

    Uni<UserModel> createUser(final Long id,
                              final String password,
                              final String idempotencyKey) {
        final var passwordHash = BcryptUtil.bcryptHash(password);
        final var user = userModelFactory.create(id, UserRoleEnum.WORKER, passwordHash, idempotencyKey);
        final var request = new SyncUserRequest(user);
        return userModule.getUserService().syncUser(request)
                .map(SyncUserResponse::getCreated)
                .replaceWith(user)
                .onFailure(ServerSideConflictException.class)
                .recoverWithUni(t -> {
                    if (t instanceof final ServerSideBaseException exception) {
                        if (exception.getQualifier().equals(ExceptionQualifierEnum.IDEMPOTENCY_VIOLATION)) {
                            log.warn("Idempotency was violated, object={}, {}", user, t.getMessage());

                            // User was already created
                            final var getUserRequest = new GetUserRequest(id);
                            return userModule.getUserService().getUser(getUserRequest)
                                    .map(GetUserResponse::getUser);

                        }
                    }

                    return Uni.createFrom().failure(t);
                });
    }

    Uni<Boolean> syncPoolRequest(final RuntimeModel runtime,
                                 final UserModel user,
                                 final String password) {
        final var rootId = getConfigOperation.getServiceConfig().bootstrap().rootId();
        return getRoot(rootId)
                .flatMap(root -> {
                    final var defaultPoolId = root.getDefaultPoolId();
                    final var poolRequestConfig = new PoolRequestConfigModel();
                    poolRequestConfig.setServerContainerConfig(new PoolRequestConfigModel.ServerContainerConfig());
                    final var workerImage = getConfigOperation.getServiceConfig().workers().dockerImage();
                    poolRequestConfig.getServerContainerConfig().setImage(workerImage);
                    poolRequestConfig.getServerContainerConfig().setCpuLimit(1000);
                    poolRequestConfig.getServerContainerConfig().setMemoryLimit(1000);
                    final var serviceUri = getConfigOperation.getServiceConfig().workers().serviceUri();
                    final var environment = new HashMap<String, String>();
                    environment.put("OMGSERVERS_URL", serviceUri.toString());
                    environment.put("OMGSERVERS_USER_ID", user.getId().toString());
                    environment.put("OMGSERVERS_PASSWORD", password);
                    environment.put("OMGSERVERS_RUNTIME_ID", runtime.getId().toString());
                    environment.put("OMGSERVERS_RUNTIME_QUALIFIER", runtime.getQualifier().toString());
                    poolRequestConfig.getServerContainerConfig().setEnvironment(environment);

                    final var poolRequest = poolRequestModelFactory.create(defaultPoolId,
                            runtime.getId(),
                            poolRequestConfig);

                    final var request = new SyncPoolRequestRequest(poolRequest);
                    return poolModule.getPoolService().syncPoolRequest(request)
                            .map(SyncPoolRequestResponse::getCreated)
                            .onFailure(ServerSideConflictException.class)
                            .recoverWithUni(t -> {
                                if (t instanceof final ServerSideBaseException exception) {
                                    if (exception.getQualifier().equals(ExceptionQualifierEnum.IDEMPOTENCY_VIOLATION)) {
                                        log.warn("Idempotency was violated, object={}, {}", poolRequest,
                                                t.getMessage());
                                        return Uni.createFrom().item(Boolean.FALSE);
                                    }
                                }

                                return Uni.createFrom().failure(t);
                            });
                });
    }

    Uni<RootModel> getRoot(final Long id) {
        final var getRootRequest = new GetRootRequest(id);
        return rootModule.getRootService().getRoot(getRootRequest)
                .map(GetRootResponse::getRoot);
    }

    Uni<Boolean> syncRuntimeRef(final RuntimeModel runtime, final String idempotencyKey) {
        final var runtimeId = runtime.getId();
        return switch (runtime.getQualifier()) {
            case LOBBY -> {
                final var lobbyId = runtime.getConfig().getLobbyConfig().getLobbyId();
                final var lobbyRuntimeRef = lobbyRuntimeRefModelFactory.create(lobbyId, runtimeId, idempotencyKey);
                final var request = new SyncLobbyRuntimeRefRequest(lobbyRuntimeRef);
                yield lobbyModule.getLobbyService().syncLobbyRuntimeRef(request)
                        .map(SyncLobbyRuntimeRefResponse::getCreated)
                        .onFailure(ServerSideNotFoundException.class)
                        .recoverWithItem(Boolean.FALSE)
                        .onFailure(ServerSideConflictException.class)
                        .recoverWithUni(t -> {
                            if (t instanceof final ServerSideBaseException exception) {
                                if (exception.getQualifier().equals(ExceptionQualifierEnum.IDEMPOTENCY_VIOLATION)) {
                                    log.warn("Idempotency was violated, object={}, {}", lobbyRuntimeRef,
                                            t.getMessage());
                                    return Uni.createFrom().item(Boolean.FALSE);
                                }
                            }

                            return Uni.createFrom().failure(t);
                        });
            }
            case MATCH -> {
                final var matchConfig = runtime.getConfig().getMatchConfig();
                final var matchmakerId = matchConfig.getMatchmakerId();
                final var matchId = matchConfig.getMatchId();
                final var matchRuntimeRef = matchmakerMatchRuntimeRefModelFactory
                        .create(matchmakerId, matchId, runtimeId, idempotencyKey);
                final var request = new SyncMatchmakerMatchRuntimeRefRequest(matchRuntimeRef);
                yield matchmakerModule.getMatchmakerService().syncMatchmakerMatchRuntimeRef(request)
                        .map(SyncMatchmakerMatchRuntimeRefResponse::getCreated)
                        .onFailure(ServerSideNotFoundException.class)
                        .recoverWithItem(Boolean.FALSE)
                        .onFailure(ServerSideConflictException.class)
                        .recoverWithUni(t -> {
                            if (t instanceof final ServerSideBaseException exception) {
                                if (exception.getQualifier().equals(ExceptionQualifierEnum.IDEMPOTENCY_VIOLATION)) {
                                    log.warn("Idempotency was violated, object={}, {}", matchRuntimeRef,
                                            t.getMessage());
                                    return Uni.createFrom().item(Boolean.FALSE);
                                }
                            }

                            return Uni.createFrom().failure(t);
                        });
            }
        };
    }

    Uni<Boolean> requestJobExecution(final Long runtimeId, final String idempotencyKey) {
        final var eventBody = new RuntimeJobTaskExecutionRequestedEventBodyModel(runtimeId);
        final var eventModel = eventModelFactory.create(eventBody,
                idempotencyKey + "/" + eventBody.getQualifier());

        final var syncEventRequest = new SyncEventRequest(eventModel);
        return systemModule.getEventService().syncEvent(syncEventRequest)
                .map(SyncEventResponse::getCreated)
                .onFailure(ServerSideConflictException.class)
                .recoverWithUni(t -> {
                    if (t instanceof final ServerSideBaseException exception) {
                        if (exception.getQualifier().equals(ExceptionQualifierEnum.IDEMPOTENCY_VIOLATION)) {
                            log.warn("Idempotency was violated, object={}, {}", eventModel, t.getMessage());
                            return Uni.createFrom().item(Boolean.FALSE);
                        }
                    }

                    return Uni.createFrom().failure(t);
                });
    }
}
