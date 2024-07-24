package com.omgservers.service.handler.runtime;

import com.omgservers.model.dto.lobby.SyncLobbyRuntimeRefRequest;
import com.omgservers.model.dto.lobby.SyncLobbyRuntimeRefResponse;
import com.omgservers.model.dto.matchmaker.SyncMatchmakerMatchRuntimeRefRequest;
import com.omgservers.model.dto.matchmaker.SyncMatchmakerMatchRuntimeRefResponse;
import com.omgservers.model.dto.runtime.GetRuntimeRequest;
import com.omgservers.model.dto.runtime.GetRuntimeResponse;
import com.omgservers.model.dto.system.SyncEventRequest;
import com.omgservers.model.dto.system.SyncEventResponse;
import com.omgservers.model.dto.system.job.SyncJobRequest;
import com.omgservers.model.dto.system.job.SyncJobResponse;
import com.omgservers.model.event.EventModel;
import com.omgservers.model.event.EventQualifierEnum;
import com.omgservers.model.event.body.internal.RuntimeDeploymentRequestedEventBodyModel;
import com.omgservers.model.event.body.module.runtime.RuntimeCreatedEventBodyModel;
import com.omgservers.model.job.JobQualifierEnum;
import com.omgservers.model.runtime.RuntimeModel;
import com.omgservers.model.exception.ExceptionQualifierEnum;
import com.omgservers.service.exception.ServerSideBaseException;
import com.omgservers.service.exception.ServerSideConflictException;
import com.omgservers.service.exception.ServerSideNotFoundException;
import com.omgservers.service.factory.lobby.LobbyRuntimeRefModelFactory;
import com.omgservers.service.factory.matchmaker.MatchmakerMatchRuntimeRefModelFactory;
import com.omgservers.service.factory.pool.PoolRequestModelFactory;
import com.omgservers.service.factory.runtime.RuntimePermissionModelFactory;
import com.omgservers.service.factory.system.EventModelFactory;
import com.omgservers.service.factory.system.JobModelFactory;
import com.omgservers.service.factory.user.UserModelFactory;
import com.omgservers.service.handler.EventHandler;
import com.omgservers.service.module.lobby.LobbyModule;
import com.omgservers.service.module.matchmaker.MatchmakerModule;
import com.omgservers.service.module.pool.PoolModule;
import com.omgservers.service.module.runtime.RuntimeModule;
import com.omgservers.service.module.system.SystemModule;
import com.omgservers.service.module.user.UserModule;
import com.omgservers.service.operation.generateSecureString.GenerateSecureStringOperation;
import com.omgservers.service.operation.getConfig.GetConfigOperation;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class RuntimeCreatedEventHandlerImpl implements EventHandler {

    final MatchmakerModule matchmakerModule;
    final RuntimeModule runtimeModule;
    final SystemModule systemModule;
    final LobbyModule lobbyModule;
    final UserModule userModule;
    final PoolModule poolModule;

    final GenerateSecureStringOperation generateSecureStringOperation;
    final GetConfigOperation getConfigOperation;

    final MatchmakerMatchRuntimeRefModelFactory matchmakerMatchRuntimeRefModelFactory;
    final RuntimePermissionModelFactory runtimePermissionModelFactory;
    final LobbyRuntimeRefModelFactory lobbyRuntimeRefModelFactory;
    final PoolRequestModelFactory poolRequestModelFactory;
    final EventModelFactory eventModelFactory;
    final UserModelFactory userModelFactory;
    final JobModelFactory jobModelFactory;

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

                    final var idempotencyKey = event.getId().toString();
                    return syncRuntimeRef(runtime, idempotencyKey)
                            .flatMap(created -> requestRuntimeDeployment(runtimeId, idempotencyKey))
                            .flatMap(created -> syncRuntimeJob(runtimeId, idempotencyKey));
                })
                .replaceWithVoid();
    }

    Uni<RuntimeModel> getRuntime(final Long runtimeId) {
        final var request = new GetRuntimeRequest(runtimeId);
        return runtimeModule.getRuntimeService().getRuntime(request)
                .map(GetRuntimeResponse::getRuntime);
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
                                if (exception.getQualifier().equals(ExceptionQualifierEnum.IDEMPOTENCY_VIOLATED)) {
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
                                if (exception.getQualifier().equals(ExceptionQualifierEnum.IDEMPOTENCY_VIOLATED)) {
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

    Uni<Boolean> requestRuntimeDeployment(final Long runtimeId,
                                          final String idempotencyKey) {
        final var eventBody = new RuntimeDeploymentRequestedEventBodyModel(runtimeId);
        final var eventModel = eventModelFactory.create(eventBody,
                idempotencyKey + "/" + eventBody.getQualifier());

        final var syncEventRequest = new SyncEventRequest(eventModel);
        return systemModule.getEventService().syncEventWithIdempotency(syncEventRequest)
                .map(SyncEventResponse::getCreated);
    }

    Uni<Boolean> syncRuntimeJob(final Long runtimeId,
                                final String idempotencyKey) {
        final var job = jobModelFactory.create(JobQualifierEnum.RUNTIME, runtimeId, idempotencyKey);

        final var syncEventRequest = new SyncJobRequest(job);
        return systemModule.getJobService().syncJobWithIdempotency(syncEventRequest)
                .map(SyncJobResponse::getCreated);
    }
}
