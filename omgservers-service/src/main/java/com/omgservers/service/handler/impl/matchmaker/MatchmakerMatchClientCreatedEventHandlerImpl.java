package com.omgservers.service.handler.impl.matchmaker;

import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
import com.omgservers.schema.model.matchmakerMatch.MatchmakerMatchModel;
import com.omgservers.schema.model.matchmakerMatchClient.MatchmakerMatchClientModel;
import com.omgservers.schema.model.runtimeAssignment.RuntimeAssignmentConfigDto;
import com.omgservers.schema.module.matchmaker.GetMatchmakerMatchClientRequest;
import com.omgservers.schema.module.matchmaker.GetMatchmakerMatchClientResponse;
import com.omgservers.schema.module.matchmaker.GetMatchmakerMatchRequest;
import com.omgservers.schema.module.matchmaker.GetMatchmakerMatchResponse;
import com.omgservers.schema.module.runtime.SyncRuntimeAssignmentRequest;
import com.omgservers.schema.module.runtime.SyncRuntimeAssignmentResponse;
import com.omgservers.service.event.EventModel;
import com.omgservers.service.event.EventQualifierEnum;
import com.omgservers.service.event.body.module.matchmaker.MatchmakerMatchClientCreatedEventBodyModel;
import com.omgservers.service.exception.ServerSideBaseException;
import com.omgservers.service.exception.ServerSideConflictException;
import com.omgservers.service.factory.runtime.RuntimeAssignmentModelFactory;
import com.omgservers.service.handler.EventHandler;
import com.omgservers.service.module.matchmaker.MatchmakerModule;
import com.omgservers.service.module.runtime.RuntimeModule;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class MatchmakerMatchClientCreatedEventHandlerImpl implements EventHandler {

    final MatchmakerModule matchmakerModule;
    final RuntimeModule runtimeModule;

    final RuntimeAssignmentModelFactory runtimeAssignmentModelFactory;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.MATCHMAKER_MATCH_CLIENT_CREATED;
    }

    @Override
    public Uni<Void> handle(final EventModel event) {
        log.debug("Handle event, {}", event);

        final var body = (MatchmakerMatchClientCreatedEventBodyModel) event.getBody();
        final var matchmakerId = body.getMatchmakerId();
        final var matchId = body.getMatchId();
        final var matchClientId = body.getId();

        return getMatchClient(matchmakerId, matchClientId)
                .flatMap(matchClient -> {
                    log.info("Created, {}", matchClient);

                    final var clientId = matchClient.getClientId();
                    return getMatch(matchmakerId, matchId)
                            .flatMap(match -> {
                                final var runtimeId = match.getRuntimeId();

                                final var idempotencyKey = event.getId().toString();
                                return syncRuntimeAssignment(runtimeId, clientId, matchClient, idempotencyKey);
                            });
                })
                .replaceWithVoid();
    }

    Uni<MatchmakerMatchClientModel> getMatchClient(final Long matchmakerId, final Long id) {
        final var request = new GetMatchmakerMatchClientRequest(matchmakerId, id);
        return matchmakerModule.getService().getMatchmakerMatchClient(request)
                .map(GetMatchmakerMatchClientResponse::getMatchClient);
    }

    Uni<MatchmakerMatchModel> getMatch(final Long matchmakerId, final Long id) {
        final var request = new GetMatchmakerMatchRequest(matchmakerId, id);
        return matchmakerModule.getService().getMatchmakerMatch(request)
                .map(GetMatchmakerMatchResponse::getMatchmakerMatch);
    }

    Uni<Boolean> syncRuntimeAssignment(final Long runtimeId,
                                       final Long clientId,
                                       final MatchmakerMatchClientModel matchClient,
                                       final String idempotencyKey) {
        final var runtimeAssignmentConfig = RuntimeAssignmentConfigDto.create();
        runtimeAssignmentConfig.setMatchClient(matchClient);
        final var runtimeAssignment = runtimeAssignmentModelFactory.create(runtimeId,
                clientId,
                runtimeAssignmentConfig,
                idempotencyKey);
        final var request = new SyncRuntimeAssignmentRequest(runtimeAssignment);
        return runtimeModule.getService().syncRuntimeAssignment(request)
                .map(SyncRuntimeAssignmentResponse::getCreated)
                .onFailure(ServerSideConflictException.class)
                .recoverWithUni(t -> {
                    if (t instanceof final ServerSideBaseException exception) {
                        if (exception.getQualifier().equals(ExceptionQualifierEnum.IDEMPOTENCY_VIOLATED)) {
                            log.warn("Idempotency was violated, object={}, {}", runtimeAssignment, t.getMessage());
                            return Uni.createFrom().item(Boolean.FALSE);
                        }
                    }

                    return Uni.createFrom().failure(t);
                });
    }
}
