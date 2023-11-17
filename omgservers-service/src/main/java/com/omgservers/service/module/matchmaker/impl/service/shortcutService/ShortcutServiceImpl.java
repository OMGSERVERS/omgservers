package com.omgservers.service.module.matchmaker.impl.service.shortcutService;

import com.omgservers.model.dto.matchmaker.DeleteMatchClientRequest;
import com.omgservers.model.dto.matchmaker.DeleteMatchClientResponse;
import com.omgservers.model.dto.matchmaker.DeleteMatchCommandRequest;
import com.omgservers.model.dto.matchmaker.DeleteMatchCommandResponse;
import com.omgservers.model.dto.matchmaker.DeleteMatchRequest;
import com.omgservers.model.dto.matchmaker.DeleteMatchResponse;
import com.omgservers.model.dto.matchmaker.DeleteMatchmakerCommandRequest;
import com.omgservers.model.dto.matchmaker.DeleteMatchmakerCommandResponse;
import com.omgservers.model.dto.matchmaker.DeleteMatchmakerRequest;
import com.omgservers.model.dto.matchmaker.DeleteMatchmakerResponse;
import com.omgservers.model.dto.matchmaker.DeleteRequestRequest;
import com.omgservers.model.dto.matchmaker.DeleteRequestResponse;
import com.omgservers.model.dto.matchmaker.GetMatchClientRequest;
import com.omgservers.model.dto.matchmaker.GetMatchClientResponse;
import com.omgservers.model.dto.matchmaker.GetMatchRequest;
import com.omgservers.model.dto.matchmaker.GetMatchResponse;
import com.omgservers.model.dto.matchmaker.GetMatchmakerRequest;
import com.omgservers.model.dto.matchmaker.GetMatchmakerResponse;
import com.omgservers.model.dto.matchmaker.GetMatchmakerStateRequest;
import com.omgservers.model.dto.matchmaker.GetMatchmakerStateResponse;
import com.omgservers.model.dto.matchmaker.SyncMatchCommandRequest;
import com.omgservers.model.dto.matchmaker.SyncMatchCommandResponse;
import com.omgservers.model.dto.matchmaker.UpdateMatchmakerStateRequest;
import com.omgservers.model.dto.matchmaker.UpdateMatchmakerStateResponse;
import com.omgservers.model.dto.matchmaker.ViewMatchClientsRequest;
import com.omgservers.model.dto.matchmaker.ViewMatchClientsResponse;
import com.omgservers.model.dto.matchmaker.ViewMatchCommandsRequest;
import com.omgservers.model.dto.matchmaker.ViewMatchCommandsResponse;
import com.omgservers.model.dto.matchmaker.ViewMatchesRequest;
import com.omgservers.model.dto.matchmaker.ViewMatchesResponse;
import com.omgservers.model.dto.matchmaker.ViewMatchmakerCommandsRequest;
import com.omgservers.model.dto.matchmaker.ViewMatchmakerCommandsResponse;
import com.omgservers.model.dto.matchmaker.ViewRequestsRequest;
import com.omgservers.model.dto.matchmaker.ViewRequestsResponse;
import com.omgservers.model.match.MatchModel;
import com.omgservers.model.matchClient.MatchClientModel;
import com.omgservers.model.matchCommand.MatchCommandModel;
import com.omgservers.model.matchmaker.MatchmakerModel;
import com.omgservers.model.matchmakerChangeOfState.MatchmakerChangeOfState;
import com.omgservers.model.matchmakerCommand.MatchmakerCommandModel;
import com.omgservers.model.matchmakerState.MatchmakerState;
import com.omgservers.model.request.RequestModel;
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
    public Uni<MatchmakerModel> getMatchmaker(final Long matchmakerId) {
        final var request = new GetMatchmakerRequest(matchmakerId);
        return matchmakerModule.getMatchmakerService().getMatchmaker(request)
                .map(GetMatchmakerResponse::getMatchmaker);
    }

    @Override
    public Uni<Boolean> deleteMatchmaker(final Long matchmakerId) {
        final var request = new DeleteMatchmakerRequest(matchmakerId);
        return matchmakerModule.getMatchmakerService().deleteMatchmaker(request)
                .map(DeleteMatchmakerResponse::getDeleted);
    }

    @Override
    public Uni<MatchmakerState> getMatchmakerState(final Long matchmakerId) {
        final var request = new GetMatchmakerStateRequest(matchmakerId);
        return matchmakerModule.getMatchmakerService().getMatchmakerState(request)
                .map(GetMatchmakerStateResponse::getMatchmakerState);
    }

    @Override
    public Uni<Boolean> updateMatchmakerState(final Long matchmakerId,
                                              final MatchmakerChangeOfState changeOfState) {
        final var request = new UpdateMatchmakerStateRequest(matchmakerId, changeOfState);
        return matchmakerModule.getMatchmakerService().updateMatchmakerState(request)
                .map(UpdateMatchmakerStateResponse::getUpdated);
    }

    @Override
    public Uni<List<MatchmakerCommandModel>> viewMatchmakerCommands(final Long matchmakerId) {
        final var request = new ViewMatchmakerCommandsRequest(matchmakerId);
        return matchmakerModule.getMatchmakerService().viewMatchmakerCommands(request)
                .map(ViewMatchmakerCommandsResponse::getMatchmakerCommands);
    }

    @Override
    public Uni<Boolean> deleteMatchmakerCommand(final Long matchmakerId, final Long id) {
        final var request = new DeleteMatchmakerCommandRequest(matchmakerId, id);
        return matchmakerModule.getMatchmakerService().deleteMatchmakerCommand(request)
                .map(DeleteMatchmakerCommandResponse::getDeleted);
    }

    @Override
    public Uni<Void> deleteMatchmakerCommands(final Long matchmakerId) {
        return viewMatchmakerCommands(matchmakerId)
                .flatMap(matchmakerCommands -> Multi.createFrom().iterable(matchmakerCommands)
                        .onItem().transformToUniAndConcatenate(matchmakerCommand ->
                                deleteMatchmakerCommand(matchmakerId, matchmakerCommand.getId())
                                        .onFailure()
                                        .recoverWithItem(t -> {
                                            log.warn("Delete matchmaker command failed, " +
                                                            "matchmakerId={}, " +
                                                            "matchmakerCommandId={}" +
                                                            "{}:{}",
                                                    matchmakerId,
                                                    matchmakerCommand.getId(),
                                                    t.getClass().getSimpleName(),
                                                    t.getMessage());
                                            return null;
                                        })
                        )
                        .collect().asList()
                        .replaceWithVoid()
                );
    }

    @Override
    public Uni<MatchModel> getMatch(final Long matchmakerId, final Long matchId) {
        final var request = new GetMatchRequest(matchmakerId, matchId);
        return matchmakerModule.getMatchmakerService().getMatch(request)
                .map(GetMatchResponse::getMatch);
    }

    @Override
    public Uni<List<MatchModel>> viewMatches(final Long matchmakerId) {
        final var request = new ViewMatchesRequest(matchmakerId);
        return matchmakerModule.getMatchmakerService().viewMatches(request)
                .map(ViewMatchesResponse::getMatches);
    }

    @Override
    public Uni<Boolean> deleteMatch(final Long matchmakerId, final Long id) {
        final var request = new DeleteMatchRequest(matchmakerId, id);
        return matchmakerModule.getMatchmakerService().deleteMatch(request)
                .map(DeleteMatchResponse::getDeleted);
    }

    @Override
    public Uni<Void> deleteMatches(final Long matchmakerId) {
        return viewMatches(matchmakerId)
                .flatMap(matches -> Multi.createFrom().iterable(matches)
                        .onItem().transformToUniAndConcatenate(match ->
                                deleteMatch(matchmakerId, match.getId())
                                        .onFailure()
                                        .recoverWithItem(t -> {
                                            log.warn("Delete match failed, " +
                                                            "matchmakerId={}, " +
                                                            "matchId={}" +
                                                            "{}:{}",
                                                    matchmakerId,
                                                    match.getId(),
                                                    t.getClass().getSimpleName(),
                                                    t.getMessage());
                                            return null;
                                        })
                        )
                        .collect().asList()
                        .replaceWithVoid()
                );
    }

    @Override
    public Uni<List<MatchCommandModel>> viewMatchCommands(final Long matchmakerId, final Long matchId) {
        final var request = new ViewMatchCommandsRequest(matchmakerId, matchId);
        return matchmakerModule.getMatchmakerService().viewMatchCommands(request)
                .map(ViewMatchCommandsResponse::getMatchCommands);
    }

    @Override
    public Uni<Boolean> syncMatchCommand(final MatchCommandModel matchCommand) {
        final var request = new SyncMatchCommandRequest(matchCommand);
        return matchmakerModule.getMatchmakerService().syncMatchCommand(request)
                .map(SyncMatchCommandResponse::getCreated);
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
                                        .onFailure()
                                        .recoverWithItem(t -> {
                                            log.warn("Delete match command failed, " +
                                                            "match={}/{}, " +
                                                            "matchCommandId={}" +
                                                            "{}:{}",
                                                    matchmakerId,
                                                    matchId,
                                                    matchCommand.getId(),
                                                    t.getClass().getSimpleName(),
                                                    t.getMessage());
                                            return null;
                                        })
                        )
                        .collect().asList()
                        .replaceWithVoid()
                );
    }

    @Override
    public Uni<MatchClientModel> getMatchClient(final Long matchmakerId, final Long id) {
        final var request = new GetMatchClientRequest(matchmakerId, id);
        return matchmakerModule.getMatchmakerService().getMatchClient(request)
                .map(GetMatchClientResponse::getMatchClient);
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

    @Override
    public Uni<List<RequestModel>> viewRequests(final Long matchmakerId) {
        final var request = new ViewRequestsRequest(matchmakerId);
        return matchmakerModule.getMatchmakerService().viewRequests(request)
                .map(ViewRequestsResponse::getRequests);
    }

    @Override
    public Uni<Boolean> deleteRequest(final Long matchmakerId, final Long id) {
        final var request = new DeleteRequestRequest(matchmakerId, id);
        return matchmakerModule.getMatchmakerService().deleteRequest(request)
                .map(DeleteRequestResponse::getDeleted);
    }

    @Override
    public Uni<Void> deleteRequests(final Long matchmakerId) {
        return viewRequests(matchmakerId)
                .flatMap(requests -> Multi.createFrom().iterable(requests)
                        .onItem().transformToUniAndConcatenate(request ->
                                deleteRequest(matchmakerId, request.getId())
                                        .onFailure()
                                        .recoverWithItem(t -> {
                                            log.warn("Delete request failed, " +
                                                            "matchmakerId={}, " +
                                                            "requestId={}" +
                                                            "{}:{}",
                                                    matchmakerId,
                                                    request.getId(),
                                                    t.getClass().getSimpleName(),
                                                    t.getMessage());
                                            return null;
                                        })
                        )
                        .collect().asList()
                        .replaceWithVoid()
                );
    }
}
