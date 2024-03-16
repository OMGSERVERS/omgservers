package com.omgservers.service.handler.matchmaker;

import com.omgservers.model.dto.matchmaker.GetMatchmakerMatchClientRequest;
import com.omgservers.model.dto.matchmaker.GetMatchmakerMatchClientResponse;
import com.omgservers.model.dto.matchmaker.GetMatchmakerMatchRequest;
import com.omgservers.model.dto.matchmaker.GetMatchmakerMatchResponse;
import com.omgservers.model.dto.matchmaker.SyncMatchCommandRequest;
import com.omgservers.model.dto.matchmaker.SyncMatchCommandResponse;
import com.omgservers.model.event.EventModel;
import com.omgservers.model.event.EventQualifierEnum;
import com.omgservers.model.event.body.MatchClientDeletedEventBodyModel;
import com.omgservers.model.matchmakerMatch.MatchmakerMatchModel;
import com.omgservers.model.matchmakerMatchClient.MatchmakerMatchClientModel;
import com.omgservers.model.matchCommand.body.DeleteClientMatchCommandBodyModel;
import com.omgservers.service.factory.MatchCommandModelFactory;
import com.omgservers.service.handler.EventHandler;
import com.omgservers.service.module.matchmaker.MatchmakerModule;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class MatchClientDeletedEventHandlerImpl implements EventHandler {

    final MatchmakerModule matchmakerModule;

    final MatchCommandModelFactory matchCommandModelFactory;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.MATCH_CLIENT_DELETED;
    }

    @Override
    public Uni<Void> handle(final EventModel event) {
        log.debug("Handle event, {}", event);

        final var body = (MatchClientDeletedEventBodyModel) event.getBody();
        final var matchmakerId = body.getMatchmakerId();
        final var matchClientId = body.getId();

        return getMatchClient(matchmakerId, matchClientId)
                .flatMap(matchClient -> {
                    final var matchId = matchClient.getMatchId();
                    final var clientId = matchClient.getClientId();

                    log.info("Match client was deleted, matchClient={}/{}, clientId={}, matchId={}",
                            matchmakerId, matchClientId, clientId, matchId);

                    return getMatch(matchmakerId, matchId)
                            .flatMap(match -> {
                                if (match.getDeleted()) {
                                    log.warn("Match was already deleted, DeleteClientMatchCommand won't be created, " +
                                            "match={}/{}", matchmakerId, matchId);
                                    return Uni.createFrom().voidItem();
                                }

                                return syncDeleteClientMatchCommand(matchmakerId, matchId, clientId);
                            });
                })
                .replaceWithVoid();
    }

    Uni<MatchmakerMatchClientModel> getMatchClient(final Long matchmakerId, final Long id) {
        final var request = new GetMatchmakerMatchClientRequest(matchmakerId, id);
        return matchmakerModule.getMatchmakerService().getMatchmakerMatchClient(request)
                .map(GetMatchmakerMatchClientResponse::getMatchClient);
    }

    Uni<MatchmakerMatchModel> getMatch(final Long matchmakerId, final Long matchId) {
        final var request = new GetMatchmakerMatchRequest(matchmakerId, matchId);
        return matchmakerModule.getMatchmakerService().getMatchmakerMatch(request)
                .map(GetMatchmakerMatchResponse::getMatchmakerMatch);
    }

    Uni<Boolean> syncDeleteClientMatchCommand(final Long matchmakerId,
                                              final Long matchId,
                                              final Long clientId) {
        final var matchCommandBody = new DeleteClientMatchCommandBodyModel(clientId);
        final var matchCommandModel = matchCommandModelFactory.create(matchmakerId, matchId, matchCommandBody);
        final var syncMatchCommandRequest = new SyncMatchCommandRequest(matchCommandModel);
        return matchmakerModule.getMatchmakerService().syncMatchmakerMatchCommand(syncMatchCommandRequest)
                .map(SyncMatchCommandResponse::getCreated);
    }
}
