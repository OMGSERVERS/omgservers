package com.omgservers.service.module.lobby.impl.service.webService.impl;

import com.omgservers.model.dto.lobby.DeleteLobbyRequest;
import com.omgservers.model.dto.lobby.DeleteLobbyResponse;
import com.omgservers.model.dto.lobby.DeleteLobbyRuntimeRequest;
import com.omgservers.model.dto.lobby.DeleteLobbyRuntimeResponse;
import com.omgservers.model.dto.lobby.FindLobbyRuntimeRequest;
import com.omgservers.model.dto.lobby.FindLobbyRuntimeResponse;
import com.omgservers.model.dto.lobby.GetLobbyRequest;
import com.omgservers.model.dto.lobby.GetLobbyResponse;
import com.omgservers.model.dto.lobby.GetLobbyRuntimeRequest;
import com.omgservers.model.dto.lobby.GetLobbyRuntimeResponse;
import com.omgservers.model.dto.lobby.SyncLobbyRequest;
import com.omgservers.model.dto.lobby.SyncLobbyResponse;
import com.omgservers.model.dto.lobby.SyncLobbyRuntimeRequest;
import com.omgservers.model.dto.lobby.SyncLobbyRuntimeResponse;
import com.omgservers.service.module.lobby.impl.service.matchmakerService.LobbyService;
import com.omgservers.service.module.lobby.impl.service.webService.WebService;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class WebServiceImpl implements WebService {

    final LobbyService lobbyService;

    @Override
    public Uni<GetLobbyResponse> getLobby(final GetLobbyRequest request) {
        return lobbyService.getLobby(request);
    }

    @Override
    public Uni<SyncLobbyResponse> syncLobby(final SyncLobbyRequest request) {
        return lobbyService.syncLobby(request);
    }

    @Override
    public Uni<DeleteLobbyResponse> deleteLobby(final DeleteLobbyRequest request) {
        return lobbyService.deleteLobby(request);
    }

    @Override
    public Uni<GetLobbyRuntimeResponse> getLobbyRuntime(final GetLobbyRuntimeRequest request) {
        return lobbyService.getLobbyRuntime(request);
    }

    @Override
    public Uni<FindLobbyRuntimeResponse> findLobbyRuntime(final FindLobbyRuntimeRequest request) {
        return lobbyService.findLobbyRuntime(request);
    }

    @Override
    public Uni<SyncLobbyRuntimeResponse> syncLobbyRuntime(final SyncLobbyRuntimeRequest request) {
        return lobbyService.syncLobbyRuntime(request);
    }

    @Override
    public Uni<DeleteLobbyRuntimeResponse> deleteLobbyRuntime(final DeleteLobbyRuntimeRequest request) {
        return lobbyService.deleteLobbyRuntime(request);
    }

    //    @Override
//    public Uni<SyncMatchmakerResponse> syncMatchmaker(final SyncMatchmakerRequest request) {
//        return lobbyService.syncMatchmaker(request);
//    }
//
//    @Override
//    public Uni<GetMatchmakerResponse> getMatchmaker(final GetMatchmakerRequest request) {
//        return lobbyService.getMatchmaker(request);
//    }
//
//    @Override
//    public Uni<DeleteMatchmakerResponse> deleteMatchmaker(final DeleteMatchmakerRequest request) {
//        return lobbyService.deleteMatchmaker(request);
//    }
//
//    @Override
//    public Uni<GetMatchmakerStateResponse> getMatchmakerState(GetMatchmakerStateRequest request) {
//        return lobbyService.getMatchmakerState(request);
//    }
//
//    @Override
//    public Uni<UpdateMatchmakerStateResponse> updateMatchmakerState(final UpdateMatchmakerStateRequest request) {
//        return lobbyService.updateMatchmakerState(request);
//    }
//
//    @Override
//    public Uni<SyncMatchmakerCommandResponse> syncMatchmakerCommand(final SyncMatchmakerCommandRequest request) {
//        return lobbyService.syncMatchmakerCommand(request);
//    }
//
//    @Override
//    public Uni<DeleteMatchmakerCommandResponse> deleteMatchmakerCommand(final DeleteMatchmakerCommandRequest request) {
//        return lobbyService.deleteMatchmakerCommand(request);
//    }
//
//    @Override
//    public Uni<ViewMatchmakerCommandsResponse> viewMatchmakerCommands(final ViewMatchmakerCommandsRequest request) {
//        return lobbyService.viewMatchmakerCommands(request);
//    }
//
//    @Override
//    public Uni<SyncRequestResponse> syncRequest(final SyncRequestRequest request) {
//        return lobbyService.syncRequest(request);
//    }
//
//    @Override
//    public Uni<DeleteRequestResponse> deleteRequest(final DeleteRequestRequest request) {
//        return lobbyService.deleteRequest(request);
//    }
//
//    @Override
//    public Uni<ViewRequestsResponse> viewRequests(final ViewRequestsRequest request) {
//        return lobbyService.viewRequests(request);
//    }
//
//    @Override
//    public Uni<GetMatchResponse> getMatch(final GetMatchRequest request) {
//        return lobbyService.getMatch(request);
//    }
//
//    @Override
//    public Uni<SyncMatchResponse> syncMatch(final SyncMatchRequest request) {
//        return lobbyService.syncMatch(request);
//    }
//
//    @Override
//    public Uni<DeleteMatchResponse> deleteMatch(final DeleteMatchRequest request) {
//        return lobbyService.deleteMatch(request);
//    }
//
//    @Override
//    public Uni<ViewMatchesResponse> viewMatches(final ViewMatchesRequest request) {
//        return lobbyService.viewMatches(request);
//    }
//
//    @Override
//    public Uni<SyncMatchCommandResponse> syncMatchCommand(final SyncMatchCommandRequest request) {
//        return lobbyService.syncMatchCommand(request);
//    }
//
//    @Override
//    public Uni<DeleteMatchCommandResponse> deleteMatchCommand(final DeleteMatchCommandRequest request) {
//        return lobbyService.deleteMatchCommand(request);
//    }
//
//    @Override
//    public Uni<ViewMatchCommandsResponse> viewMatchCommands(final ViewMatchCommandsRequest request) {
//        return lobbyService.viewMatchCommands(request);
//    }
//
//    @Override
//    public Uni<GetMatchClientResponse> getMatchClient(final GetMatchClientRequest request) {
//        return lobbyService.getMatchClient(request);
//    }
//
//    @Override
//    public Uni<SyncMatchClientResponse> syncMatchClient(final SyncMatchClientRequest request) {
//        return lobbyService.syncMatchClient(request);
//    }
//
//    @Override
//    public Uni<DeleteMatchClientResponse> deleteMatchClient(final DeleteMatchClientRequest request) {
//        return lobbyService.deleteMatchClient(request);
//    }
//
//    @Override
//    public Uni<FindMatchClientResponse> findMatchClient(final FindMatchClientRequest request) {
//        return lobbyService.findMatchClient(request);
//    }
//
//    @Override
//    public Uni<ViewMatchClientsResponse> viewMatchClients(ViewMatchClientsRequest request) {
//        return lobbyService.viewMatchClients(request);
//    }
}
