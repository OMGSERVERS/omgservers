package com.omgservers.service.handler;

import com.omgservers.model.dto.matchmaker.GetMatchClientRequest;
import com.omgservers.model.dto.matchmaker.GetMatchClientResponse;
import com.omgservers.model.dto.matchmaker.SyncMatchCommandRequest;
import com.omgservers.model.dto.matchmaker.SyncMatchCommandResponse;
import com.omgservers.model.event.EventModel;
import com.omgservers.model.event.EventQualifierEnum;
import com.omgservers.model.event.body.MatchClientDeletedEventBodyModel;
import com.omgservers.model.matchClient.MatchClientModel;
import com.omgservers.model.matchCommand.body.DeleteClientMatchCommandBodyModel;
import com.omgservers.service.factory.MatchCommandModelFactory;
import com.omgservers.service.module.matchmaker.MatchmakerModule;
import com.omgservers.service.module.system.impl.service.handlerService.impl.EventHandler;
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
    public Uni<Boolean> handle(EventModel event) {
        final var body = (MatchClientDeletedEventBodyModel) event.getBody();
        final var matchmakerId = body.getMatchmakerId();
        final var matchClientId = body.getId();

        return getDeletedMatchClient(matchmakerId, matchClientId)
                .flatMap(matchClient -> {
                    final var clientId = matchClient.getClientId();
                    final var matchId = matchClient.getMatchId();
                    final var userId = matchClient.getUserId();

                    log.info("Match client was deleted, {}/{}, clientId={}, matchId={}",
                            matchmakerId, matchClientId, clientId, matchId);

                    return syncDeleteClientMatchCommand(matchmakerId, matchId, userId, clientId);
                })
                .replaceWith(true);

    }

    Uni<MatchClientModel> getDeletedMatchClient(final Long matchmakerId, final Long id) {
        final var request = new GetMatchClientRequest(matchmakerId, id, true);
        return matchmakerModule.getMatchmakerService().getMatchClient(request)
                .map(GetMatchClientResponse::getMatchClient);
    }

    Uni<Boolean> syncDeleteClientMatchCommand(final Long matchmakerId,
                                              final Long matchId,
                                              final Long userId,
                                              final Long clientId) {
        final var matchCommandBody = new DeleteClientMatchCommandBodyModel(userId, clientId);
        final var matchCommandModel = matchCommandModelFactory.create(matchmakerId, matchId, matchCommandBody);
        final var syncMatchCommandRequest = new SyncMatchCommandRequest(matchCommandModel);
        return matchmakerModule.getMatchmakerService().syncMatchCommand(syncMatchCommandRequest)
                .map(SyncMatchCommandResponse::getCreated);
    }
}
