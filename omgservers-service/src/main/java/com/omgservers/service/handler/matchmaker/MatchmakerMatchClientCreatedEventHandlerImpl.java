package com.omgservers.service.handler.matchmaker;

import com.omgservers.model.dto.matchmaker.GetMatchmakerMatchClientRequest;
import com.omgservers.model.dto.matchmaker.GetMatchmakerMatchClientResponse;
import com.omgservers.model.dto.matchmaker.GetMatchmakerMatchRequest;
import com.omgservers.model.dto.matchmaker.GetMatchmakerMatchResponse;
import com.omgservers.model.dto.runtime.SyncRuntimeClientRequest;
import com.omgservers.model.dto.runtime.SyncRuntimeClientResponse;
import com.omgservers.model.event.EventModel;
import com.omgservers.model.event.EventQualifierEnum;
import com.omgservers.model.event.body.module.MatchmakerMatchClientCreatedEventBodyModel;
import com.omgservers.model.matchmakerMatch.MatchmakerMatchModel;
import com.omgservers.model.matchmakerMatchClient.MatchmakerMatchClientModel;
import com.omgservers.model.runtimeClient.RuntimeClientConfigModel;
import com.omgservers.service.exception.ExceptionQualifierEnum;
import com.omgservers.service.exception.ServerSideBaseException;
import com.omgservers.service.exception.ServerSideConflictException;
import com.omgservers.service.factory.RuntimeClientModelFactory;
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

    final RuntimeClientModelFactory runtimeClientModelFactory;

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
                    final var clientId = matchClient.getClientId();

                    log.info("Matchmaker match client was created, id={}, match={}/{}, clientId={}",
                            matchClient.getId(), matchmakerId, matchId, clientId);

                    return getMatch(matchmakerId, matchId)
                            .flatMap(match -> {
                                final var runtimeId = match.getRuntimeId();

                                final var idempotencyKey = event.getIdempotencyKey();
                                return syncRuntimeClient(runtimeId, clientId, matchClient, idempotencyKey);
                            });
                })
                .replaceWithVoid();
    }

    Uni<MatchmakerMatchClientModel> getMatchClient(final Long matchmakerId, final Long id) {
        final var request = new GetMatchmakerMatchClientRequest(matchmakerId, id);
        return matchmakerModule.getMatchmakerService().getMatchmakerMatchClient(request)
                .map(GetMatchmakerMatchClientResponse::getMatchClient);
    }

    Uni<MatchmakerMatchModel> getMatch(final Long matchmakerId, final Long id) {
        final var request = new GetMatchmakerMatchRequest(matchmakerId, id);
        return matchmakerModule.getMatchmakerService().getMatchmakerMatch(request)
                .map(GetMatchmakerMatchResponse::getMatchmakerMatch);
    }

    Uni<Boolean> syncRuntimeClient(final Long runtimeId,
                                   final Long clientId,
                                   final MatchmakerMatchClientModel matchClient,
                                   final String idempotencyKey) {
        final var runtimeClientConfig = RuntimeClientConfigModel.create();
        runtimeClientConfig.setMatchClient(matchClient);
        final var runtimeClient = runtimeClientModelFactory.create(runtimeId,
                clientId,
                runtimeClientConfig,
                idempotencyKey);
        final var request = new SyncRuntimeClientRequest(runtimeClient);
        return runtimeModule.getRuntimeService().syncRuntimeClient(request)
                .map(SyncRuntimeClientResponse::getCreated)
                .onFailure(ServerSideConflictException.class)
                .recoverWithUni(t -> {
                    if (t instanceof final ServerSideBaseException exception) {
                        if (exception.getQualifier().equals(ExceptionQualifierEnum.IDEMPOTENCY_VIOLATION)) {
                            log.warn("Idempotency was violated, object={}, {}", runtimeClient, t.getMessage());
                            return Uni.createFrom().item(Boolean.FALSE);
                        }
                    }

                    return Uni.createFrom().failure(t);
                });
    }
}
