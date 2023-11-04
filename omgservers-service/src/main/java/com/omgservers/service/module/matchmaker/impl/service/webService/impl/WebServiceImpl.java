package com.omgservers.service.module.matchmaker.impl.service.webService.impl;

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
import com.omgservers.model.dto.matchmaker.FindMatchClientRequest;
import com.omgservers.model.dto.matchmaker.FindMatchClientResponse;
import com.omgservers.model.dto.matchmaker.GetMatchClientRequest;
import com.omgservers.model.dto.matchmaker.GetMatchClientResponse;
import com.omgservers.model.dto.matchmaker.GetMatchRequest;
import com.omgservers.model.dto.matchmaker.GetMatchResponse;
import com.omgservers.model.dto.matchmaker.GetMatchmakerRequest;
import com.omgservers.model.dto.matchmaker.GetMatchmakerResponse;
import com.omgservers.model.dto.matchmaker.GetMatchmakerStateRequest;
import com.omgservers.model.dto.matchmaker.GetMatchmakerStateResponse;
import com.omgservers.model.dto.matchmaker.SyncMatchClientRequest;
import com.omgservers.model.dto.matchmaker.SyncMatchClientResponse;
import com.omgservers.model.dto.matchmaker.SyncMatchCommandRequest;
import com.omgservers.model.dto.matchmaker.SyncMatchCommandResponse;
import com.omgservers.model.dto.matchmaker.SyncMatchRequest;
import com.omgservers.model.dto.matchmaker.SyncMatchResponse;
import com.omgservers.model.dto.matchmaker.SyncMatchmakerCommandRequest;
import com.omgservers.model.dto.matchmaker.SyncMatchmakerCommandResponse;
import com.omgservers.model.dto.matchmaker.SyncMatchmakerRequest;
import com.omgservers.model.dto.matchmaker.SyncMatchmakerResponse;
import com.omgservers.model.dto.matchmaker.SyncRequestRequest;
import com.omgservers.model.dto.matchmaker.SyncRequestResponse;
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
import com.omgservers.service.module.matchmaker.impl.service.matchmakerService.MatchmakerService;
import com.omgservers.service.module.matchmaker.impl.service.webService.WebService;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class WebServiceImpl implements WebService {

    final MatchmakerService matchmakerService;

    @Override
    public Uni<SyncMatchmakerResponse> syncMatchmaker(final SyncMatchmakerRequest request) {
        return matchmakerService.syncMatchmaker(request);
    }

    @Override
    public Uni<GetMatchmakerResponse> getMatchmaker(final GetMatchmakerRequest request) {
        return matchmakerService.getMatchmaker(request);
    }

    @Override
    public Uni<DeleteMatchmakerResponse> deleteMatchmaker(final DeleteMatchmakerRequest request) {
        return matchmakerService.deleteMatchmaker(request);
    }

    @Override
    public Uni<GetMatchmakerStateResponse> getMatchmakerState(GetMatchmakerStateRequest request) {
        return matchmakerService.getMatchmakerState(request);
    }

    @Override
    public Uni<UpdateMatchmakerStateResponse> updateMatchmakerState(final UpdateMatchmakerStateRequest request) {
        return matchmakerService.updateMatchmakerState(request);
    }

    @Override
    public Uni<SyncMatchmakerCommandResponse> syncMatchmakerCommand(final SyncMatchmakerCommandRequest request) {
        return matchmakerService.syncMatchmakerCommand(request);
    }

    @Override
    public Uni<DeleteMatchmakerCommandResponse> deleteMatchmakerCommand(final DeleteMatchmakerCommandRequest request) {
        return matchmakerService.deleteMatchmakerCommand(request);
    }

    @Override
    public Uni<ViewMatchmakerCommandsResponse> viewMatchmakerCommands(final ViewMatchmakerCommandsRequest request) {
        return matchmakerService.viewMatchmakerCommands(request);
    }

    @Override
    public Uni<SyncRequestResponse> syncRequest(final SyncRequestRequest request) {
        return matchmakerService.syncRequest(request);
    }

    @Override
    public Uni<DeleteRequestResponse> deleteRequest(final DeleteRequestRequest request) {
        return matchmakerService.deleteRequest(request);
    }

    @Override
    public Uni<ViewRequestsResponse> viewRequests(final ViewRequestsRequest request) {
        return matchmakerService.viewRequests(request);
    }

    @Override
    public Uni<GetMatchResponse> getMatch(final GetMatchRequest request) {
        return matchmakerService.getMatch(request);
    }

    @Override
    public Uni<SyncMatchResponse> syncMatch(final SyncMatchRequest request) {
        return matchmakerService.syncMatch(request);
    }

    @Override
    public Uni<DeleteMatchResponse> deleteMatch(final DeleteMatchRequest request) {
        return matchmakerService.deleteMatch(request);
    }

    @Override
    public Uni<ViewMatchesResponse> viewMatches(final ViewMatchesRequest request) {
        return matchmakerService.viewMatches(request);
    }

    @Override
    public Uni<SyncMatchCommandResponse> syncMatchCommand(final SyncMatchCommandRequest request) {
        return matchmakerService.syncMatchCommand(request);
    }

    @Override
    public Uni<DeleteMatchCommandResponse> deleteMatchCommand(final DeleteMatchCommandRequest request) {
        return matchmakerService.deleteMatchCommand(request);
    }

    @Override
    public Uni<ViewMatchCommandsResponse> viewMatchCommands(final ViewMatchCommandsRequest request) {
        return matchmakerService.viewMatchCommands(request);
    }

    @Override
    public Uni<GetMatchClientResponse> getMatchClient(final GetMatchClientRequest request) {
        return matchmakerService.getMatchClient(request);
    }

    @Override
    public Uni<SyncMatchClientResponse> syncMatchClient(final SyncMatchClientRequest request) {
        return matchmakerService.syncMatchClient(request);
    }

    @Override
    public Uni<DeleteMatchClientResponse> deleteMatchClient(final DeleteMatchClientRequest request) {
        return matchmakerService.deleteMatchClient(request);
    }

    @Override
    public Uni<FindMatchClientResponse> findMatchClient(final FindMatchClientRequest request) {
        return matchmakerService.findMatchClient(request);
    }

    @Override
    public Uni<ViewMatchClientsResponse> viewMatchClients(ViewMatchClientsRequest request) {
        return matchmakerService.viewMatchClients(request);
    }
}
