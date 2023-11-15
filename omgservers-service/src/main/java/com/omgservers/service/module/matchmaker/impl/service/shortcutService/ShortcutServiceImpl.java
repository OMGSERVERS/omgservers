package com.omgservers.service.module.matchmaker.impl.service.shortcutService;

import com.omgservers.model.dto.matchmaker.DeleteMatchClientRequest;
import com.omgservers.model.dto.matchmaker.DeleteMatchClientResponse;
import com.omgservers.model.dto.matchmaker.DeleteMatchCommandRequest;
import com.omgservers.model.dto.matchmaker.DeleteMatchCommandResponse;
import com.omgservers.model.dto.matchmaker.GetMatchRequest;
import com.omgservers.model.dto.matchmaker.GetMatchResponse;
import com.omgservers.model.dto.matchmaker.ViewMatchClientsRequest;
import com.omgservers.model.dto.matchmaker.ViewMatchClientsResponse;
import com.omgservers.model.dto.matchmaker.ViewMatchCommandsRequest;
import com.omgservers.model.dto.matchmaker.ViewMatchCommandsResponse;
import com.omgservers.model.match.MatchModel;
import com.omgservers.model.matchClient.MatchClientModel;
import com.omgservers.model.matchCommand.MatchCommandModel;
import com.omgservers.service.module.matchmaker.MatchmakerModule;
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
class ShortcutServiceImpl implements ShortcutService {

    final MatchmakerModule matchmakerModule;

    @Override
    public Uni<MatchModel> getMatch(final Long matchmakerId, final Long matchId) {
        final var request = new GetMatchRequest(matchmakerId, matchId);
        return matchmakerModule.getMatchmakerService().getMatch(request)
                .map(GetMatchResponse::getMatch);
    }

    @Override
    public Uni<List<MatchCommandModel>> viewMatchCommands(final Long matchmakerId, final Long matchId) {
        final var request = new ViewMatchCommandsRequest(matchmakerId, matchId);
        return matchmakerModule.getMatchmakerService().viewMatchCommands(request)
                .map(ViewMatchCommandsResponse::getMatchCommands);
    }

    @Override
    public Uni<Boolean> deleteMatchCommand(final Long matchmakerId, final Long id) {
        final var request = new DeleteMatchCommandRequest(matchmakerId, id);
        return matchmakerModule.getMatchmakerService().deleteMatchCommand(request)
                .map(DeleteMatchCommandResponse::getDeleted);
    }

    @Override
    public Uni<Void> deleteMatchCommands(final Long matchmakerId, final Long matchId) {
        return viewMatchCommands(matchmakerId, matchId)
                .flatMap(matchCommands -> Multi.createFrom().iterable(matchCommands)
                        .onItem().transformToUniAndConcatenate(matchCommand ->
                                deleteMatchCommand(matchmakerId, matchCommand.getId())
                        )
                        .collect().asList()
                        .replaceWithVoid()
                );
    }

    @Override
    public Uni<List<MatchClientModel>> viewMatchClients(final Long matchmakerId, final Long matchId) {
        final var request = new ViewMatchClientsRequest(matchmakerId, matchId);
        return matchmakerModule.getMatchmakerService().viewMatchClients(request)
                .map(ViewMatchClientsResponse::getMatchClients);
    }

    @Override
    public Uni<Boolean> deleteMatchClient(final Long matchmakerId, final Long id) {
        final var request = new DeleteMatchClientRequest(matchmakerId, id);
        return matchmakerModule.getMatchmakerService().deleteMatchClient(request)
                .map(DeleteMatchClientResponse::getDeleted);
    }

    @Override
    public Uni<Void> deleteMatchClients(final Long matchmakerId, final Long matchId) {
        return viewMatchClients(matchmakerId, matchId)
                .flatMap(matchClients -> Multi.createFrom().iterable(matchClients)
                        .onItem().transformToUniAndConcatenate(matchClient ->
                                deleteMatchClient(matchmakerId, matchClient.getId())
                        )
                        .collect().asList()
                        .replaceWithVoid()
                );
    }
}
