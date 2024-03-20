package com.omgservers.service.handler.matchmaker;

import com.omgservers.model.dto.matchmaker.DeleteMatchmakerMatchClientRequest;
import com.omgservers.model.dto.matchmaker.DeleteMatchmakerMatchClientResponse;
import com.omgservers.model.dto.matchmaker.GetMatchmakerMatchRequest;
import com.omgservers.model.dto.matchmaker.GetMatchmakerMatchResponse;
import com.omgservers.model.dto.matchmaker.ViewMatchmakerMatchClientsRequest;
import com.omgservers.model.dto.matchmaker.ViewMatchmakerMatchClientsResponse;
import com.omgservers.model.dto.runtime.DeleteRuntimeRequest;
import com.omgservers.model.dto.runtime.DeleteRuntimeResponse;
import com.omgservers.model.event.EventModel;
import com.omgservers.model.event.EventQualifierEnum;
import com.omgservers.model.event.body.module.MatchmakerMatchDeletedEventBodyModel;
import com.omgservers.model.matchmakerMatch.MatchmakerMatchModel;
import com.omgservers.model.matchmakerMatchClient.MatchmakerMatchClientModel;
import com.omgservers.service.handler.EventHandler;
import com.omgservers.service.module.matchmaker.MatchmakerModule;
import com.omgservers.service.module.runtime.RuntimeModule;
import com.omgservers.service.module.system.SystemModule;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class MatchmakerMatchDeletedEventHandlerImpl implements EventHandler {

    final MatchmakerModule matchmakerModule;
    final RuntimeModule runtimeModule;
    final SystemModule systemModule;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.MATCHMAKER_MATCH_DELETED;
    }

    @Override
    public Uni<Void> handle(final EventModel event) {
        log.debug("Handle event, {}", event);

        final var body = (MatchmakerMatchDeletedEventBodyModel) event.getBody();
        final var matchmakerId = body.getMatchmakerId();
        final var matchId = body.getId();

        return getMatch(matchmakerId, matchId)
                .flatMap(match -> {
                    final var runtimeId = match.getRuntimeId();
                    log.info("Match was deleted, " +
                                    "match={}/{}, " +
                                    "mode={}, " +
                                    "runtimeId={}",
                            matchmakerId,
                            matchId,
                            match.getConfig().getModeConfig().getName(),
                            runtimeId);

                    return deleteRuntime(runtimeId)
                            .flatMap(voidItem -> deleteMatchClients(matchmakerId, matchId));
                })
                .replaceWithVoid();
    }

    Uni<MatchmakerMatchModel> getMatch(final Long matchmakerId, final Long matchId) {
        final var request = new GetMatchmakerMatchRequest(matchmakerId, matchId);
        return matchmakerModule.getMatchmakerService().getMatchmakerMatch(request)
                .map(GetMatchmakerMatchResponse::getMatchmakerMatch);
    }

    Uni<Boolean> deleteRuntime(final Long runtimeId) {
        final var request = new DeleteRuntimeRequest(runtimeId);
        return runtimeModule.getRuntimeService().deleteRuntime(request)
                .map(DeleteRuntimeResponse::getDeleted);
    }

    Uni<Void> deleteMatchClients(final Long matchmakerId, final Long matchId) {
        return viewMatchClients(matchmakerId, matchId)
                .flatMap(matchClients -> Multi.createFrom().iterable(matchClients)
                        .onItem().transformToUniAndConcatenate(matchClient ->
                                deleteMatchClient(matchmakerId, matchClient.getId())
                                        .onFailure()
                                        .recoverWithItem(t -> {
                                            log.warn("Delete match client failed, " +
                                                            "match={}/{}, " +
                                                            "matchClientId={}" +
                                                            "{}:{}",
                                                    matchmakerId,
                                                    matchId,
                                                    matchClient.getId(),
                                                    t.getClass().getSimpleName(),
                                                    t.getMessage());
                                            return null;
                                        })
                        )
                        .collect().asList()
                        .replaceWithVoid()
                );
    }

    Uni<List<MatchmakerMatchClientModel>> viewMatchClients(final Long matchmakerId, final Long matchId) {
        final var request = new ViewMatchmakerMatchClientsRequest(matchmakerId, matchId);
        return matchmakerModule.getMatchmakerService().viewMatchmakerMatchClients(request)
                .map(ViewMatchmakerMatchClientsResponse::getMatchClients);
    }

    Uni<Boolean> deleteMatchClient(final Long matchmakerId, final Long id) {
        final var request = new DeleteMatchmakerMatchClientRequest(matchmakerId, id);
        return matchmakerModule.getMatchmakerService().deleteMatchmakerMatchClient(request)
                .map(DeleteMatchmakerMatchClientResponse::getDeleted);
    }
}
