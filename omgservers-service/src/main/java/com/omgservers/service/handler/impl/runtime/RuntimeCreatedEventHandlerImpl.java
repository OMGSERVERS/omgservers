package com.omgservers.service.handler.impl.runtime;

import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
import com.omgservers.schema.model.job.JobQualifierEnum;
import com.omgservers.schema.model.runtime.RuntimeModel;
import com.omgservers.schema.module.lobby.SyncLobbyRuntimeRefRequest;
import com.omgservers.schema.module.lobby.SyncLobbyRuntimeRefResponse;
import com.omgservers.schema.module.matchmaker.SyncMatchmakerMatchRuntimeRefRequest;
import com.omgservers.schema.module.matchmaker.SyncMatchmakerMatchRuntimeRefResponse;
import com.omgservers.schema.module.runtime.GetRuntimeRequest;
import com.omgservers.schema.module.runtime.GetRuntimeResponse;
import com.omgservers.service.event.EventModel;
import com.omgservers.service.event.EventQualifierEnum;
import com.omgservers.service.event.body.internal.RuntimeDeploymentRequestedEventBodyModel;
import com.omgservers.service.event.body.module.runtime.RuntimeCreatedEventBodyModel;
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
import com.omgservers.service.module.user.UserModule;
import com.omgservers.service.operation.generateSecureString.GenerateSecureStringOperation;
import com.omgservers.service.operation.getServiceConfig.GetServiceConfigOperation;
import com.omgservers.service.service.event.EventService;
import com.omgservers.service.service.event.dto.SyncEventRequest;
import com.omgservers.service.service.event.dto.SyncEventResponse;
import com.omgservers.service.service.job.JobService;
import com.omgservers.service.service.job.dto.SyncJobRequest;
import com.omgservers.service.service.job.dto.SyncJobResponse;
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
    final LobbyModule lobbyModule;
    final UserModule userModule;
    final PoolModule poolModule;

    final EventService eventService;
    final JobService jobService;

    final GenerateSecureStringOperation generateSecureStringOperation;
    final GetServiceConfigOperation getServiceConfigOperation;

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
        log.trace("Handle event, {}", event);

        final var body = (RuntimeCreatedEventBodyModel) event.getBody();
        final var runtimeId = body.getId();

        final var idempotencyKey = event.getId().toString();

        return getRuntime(runtimeId)
                .flatMap(runtime -> {
                    log.debug("Created, {}", runtime);

                    return syncRuntimeRef(runtime, idempotencyKey)
                            .flatMap(created -> requestRuntimeDeployment(runtimeId, idempotencyKey))
                            .flatMap(created -> syncRuntimeJob(runtimeId, idempotencyKey));
                })
                .replaceWithVoid();
    }

    Uni<RuntimeModel> getRuntime(final Long runtimeId) {
        final var request = new GetRuntimeRequest(runtimeId);
        return runtimeModule.getService().execute(request)
                .map(GetRuntimeResponse::getRuntime);
    }

    Uni<Boolean> syncRuntimeRef(final RuntimeModel runtime, final String idempotencyKey) {
        final var runtimeId = runtime.getId();
        return switch (runtime.getQualifier()) {
            case LOBBY -> {
                final var lobbyId = runtime.getConfig().getLobbyConfig().getLobbyId();
                final var lobbyRuntimeRef = lobbyRuntimeRefModelFactory.create(lobbyId, runtimeId, idempotencyKey);
                final var request = new SyncLobbyRuntimeRefRequest(lobbyRuntimeRef);
                yield lobbyModule.getService().syncLobbyRuntimeRef(request)
                        .map(SyncLobbyRuntimeRefResponse::getCreated)
                        .onFailure(ServerSideNotFoundException.class)
                        .recoverWithItem(Boolean.FALSE)
                        .onFailure(ServerSideConflictException.class)
                        .recoverWithUni(t -> {
                            if (t instanceof final ServerSideBaseException exception) {
                                if (exception.getQualifier().equals(ExceptionQualifierEnum.IDEMPOTENCY_VIOLATED)) {
                                    log.debug("Idempotency was violated, object={}, {}", lobbyRuntimeRef,
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
                yield matchmakerModule.getService().execute(request)
                        .map(SyncMatchmakerMatchRuntimeRefResponse::getCreated)
                        .onFailure(ServerSideNotFoundException.class)
                        .recoverWithItem(Boolean.FALSE)
                        .onFailure(ServerSideConflictException.class)
                        .recoverWithUni(t -> {
                            if (t instanceof final ServerSideBaseException exception) {
                                if (exception.getQualifier().equals(ExceptionQualifierEnum.IDEMPOTENCY_VIOLATED)) {
                                    log.debug("Idempotency was violated, object={}, {}", matchRuntimeRef,
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
        return eventService.syncEventWithIdempotency(syncEventRequest)
                .map(SyncEventResponse::getCreated);
    }

    Uni<Boolean> syncRuntimeJob(final Long runtimeId,
                                final String idempotencyKey) {
        final var job = jobModelFactory.create(JobQualifierEnum.RUNTIME, runtimeId, runtimeId, idempotencyKey);

        final var syncEventRequest = new SyncJobRequest(job);
        return jobService.syncJobWithIdempotency(syncEventRequest)
                .map(SyncJobResponse::getCreated);
    }
}
