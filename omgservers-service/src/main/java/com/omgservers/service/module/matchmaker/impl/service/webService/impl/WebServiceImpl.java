package com.omgservers.service.module.matchmaker.impl.service.webService.impl;

import com.omgservers.model.dto.matchmaker.DeleteMatchCommandRequest;
import com.omgservers.model.dto.matchmaker.DeleteMatchCommandResponse;
import com.omgservers.model.dto.matchmaker.DeleteMatchmakerCommandRequest;
import com.omgservers.model.dto.matchmaker.DeleteMatchmakerCommandResponse;
import com.omgservers.model.dto.matchmaker.DeleteMatchmakerMatchClientRequest;
import com.omgservers.model.dto.matchmaker.DeleteMatchmakerMatchClientResponse;
import com.omgservers.model.dto.matchmaker.DeleteMatchmakerMatchRequest;
import com.omgservers.model.dto.matchmaker.DeleteMatchmakerMatchResponse;
import com.omgservers.model.dto.matchmaker.DeleteMatchmakerMatchRuntimeRefRequest;
import com.omgservers.model.dto.matchmaker.DeleteMatchmakerMatchRuntimeRefResponse;
import com.omgservers.model.dto.matchmaker.DeleteMatchmakerRequest;
import com.omgservers.model.dto.matchmaker.DeleteMatchmakerRequestRequest;
import com.omgservers.model.dto.matchmaker.DeleteMatchmakerRequestResponse;
import com.omgservers.model.dto.matchmaker.DeleteMatchmakerResponse;
import com.omgservers.model.dto.matchmaker.FindMatchmakerMatchClientRequest;
import com.omgservers.model.dto.matchmaker.FindMatchmakerMatchClientResponse;
import com.omgservers.model.dto.matchmaker.FindMatchmakerMatchRuntimeRefRequest;
import com.omgservers.model.dto.matchmaker.FindMatchmakerMatchRuntimeRefResponse;
import com.omgservers.model.dto.matchmaker.GetMatchmakerMatchClientRequest;
import com.omgservers.model.dto.matchmaker.GetMatchmakerMatchClientResponse;
import com.omgservers.model.dto.matchmaker.GetMatchmakerMatchRequest;
import com.omgservers.model.dto.matchmaker.GetMatchmakerMatchResponse;
import com.omgservers.model.dto.matchmaker.GetMatchmakerMatchRuntimeRefRequest;
import com.omgservers.model.dto.matchmaker.GetMatchmakerMatchRuntimeRefResponse;
import com.omgservers.model.dto.matchmaker.GetMatchmakerRequest;
import com.omgservers.model.dto.matchmaker.GetMatchmakerResponse;
import com.omgservers.model.dto.matchmaker.GetMatchmakerStateRequest;
import com.omgservers.model.dto.matchmaker.GetMatchmakerStateResponse;
import com.omgservers.model.dto.matchmaker.SyncMatchCommandRequest;
import com.omgservers.model.dto.matchmaker.SyncMatchCommandResponse;
import com.omgservers.model.dto.matchmaker.SyncMatchmakerCommandRequest;
import com.omgservers.model.dto.matchmaker.SyncMatchmakerCommandResponse;
import com.omgservers.model.dto.matchmaker.SyncMatchmakerMatchClientRequest;
import com.omgservers.model.dto.matchmaker.SyncMatchmakerMatchClientResponse;
import com.omgservers.model.dto.matchmaker.SyncMatchmakerMatchRequest;
import com.omgservers.model.dto.matchmaker.SyncMatchmakerMatchResponse;
import com.omgservers.model.dto.matchmaker.SyncMatchmakerMatchRuntimeRefRequest;
import com.omgservers.model.dto.matchmaker.SyncMatchmakerMatchRuntimeRefResponse;
import com.omgservers.model.dto.matchmaker.SyncMatchmakerRequest;
import com.omgservers.model.dto.matchmaker.SyncMatchmakerRequestRequest;
import com.omgservers.model.dto.matchmaker.SyncMatchmakerRequestResponse;
import com.omgservers.model.dto.matchmaker.SyncMatchmakerResponse;
import com.omgservers.model.dto.matchmaker.UpdateMatchmakerMatchStatusRequest;
import com.omgservers.model.dto.matchmaker.UpdateMatchmakerMatchStatusResponse;
import com.omgservers.model.dto.matchmaker.UpdateMatchmakerStateRequest;
import com.omgservers.model.dto.matchmaker.UpdateMatchmakerStateResponse;
import com.omgservers.model.dto.matchmaker.ViewMatchmakerCommandsRequest;
import com.omgservers.model.dto.matchmaker.ViewMatchmakerCommandsResponse;
import com.omgservers.model.dto.matchmaker.ViewMatchmakerMatchClientsRequest;
import com.omgservers.model.dto.matchmaker.ViewMatchmakerMatchClientsResponse;
import com.omgservers.model.dto.matchmaker.ViewMatchmakerMatchCommandsRequest;
import com.omgservers.model.dto.matchmaker.ViewMatchmakerMatchCommandsResponse;
import com.omgservers.model.dto.matchmaker.ViewMatchmakerMatchesRequest;
import com.omgservers.model.dto.matchmaker.ViewMatchmakerMatchesResponse;
import com.omgservers.model.dto.matchmaker.ViewMatchmakerRequestsRequest;
import com.omgservers.model.dto.matchmaker.ViewMatchmakerRequestsResponse;
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
    public Uni<SyncMatchmakerRequestResponse> syncMatchmakerRequest(final SyncMatchmakerRequestRequest request) {
        return matchmakerService.syncMatchmakerRequest(request);
    }

    @Override
    public Uni<DeleteMatchmakerRequestResponse> deleteMatchmakerRequest(final DeleteMatchmakerRequestRequest request) {
        return matchmakerService.deleteMatchmakerRequest(request);
    }

    @Override
    public Uni<ViewMatchmakerRequestsResponse> viewMatchmakerRequests(final ViewMatchmakerRequestsRequest request) {
        return matchmakerService.viewMatchmakerRequests(request);
    }

    @Override
    public Uni<GetMatchmakerMatchResponse> getMatchmakerMatch(final GetMatchmakerMatchRequest request) {
        return matchmakerService.getMatchmakerMatch(request);
    }

    @Override
    public Uni<UpdateMatchmakerMatchStatusResponse> updateMatchmakerMatchStatus(
            final UpdateMatchmakerMatchStatusRequest request) {
        return matchmakerService.updateMatchmakerMatchStatus(request);
    }

    @Override
    public Uni<SyncMatchmakerMatchResponse> syncMatchmakerMatch(final SyncMatchmakerMatchRequest request) {
        return matchmakerService.syncMatchmakerMatch(request);
    }

    @Override
    public Uni<DeleteMatchmakerMatchResponse> deleteMatchmakerMatch(final DeleteMatchmakerMatchRequest request) {
        return matchmakerService.deleteMatchmakerMatch(request);
    }

    @Override
    public Uni<ViewMatchmakerMatchesResponse> viewMatchmakerMatches(final ViewMatchmakerMatchesRequest request) {
        return matchmakerService.viewMatchmakerMatches(request);
    }

    @Override
    public Uni<SyncMatchCommandResponse> syncMatchmakerMatchCommand(final SyncMatchCommandRequest request) {
        return matchmakerService.syncMatchmakerMatchCommand(request);
    }

    @Override
    public Uni<DeleteMatchCommandResponse> deleteMatchmakerMatchCommand(final DeleteMatchCommandRequest request) {
        return matchmakerService.deleteMatchmakerMatchCommand(request);
    }

    @Override
    public Uni<ViewMatchmakerMatchCommandsResponse> viewMatchmakerMatchCommands(
            final ViewMatchmakerMatchCommandsRequest request) {
        return matchmakerService.viewMatchmakerMatchCommands(request);
    }

    @Override
    public Uni<GetMatchmakerMatchClientResponse> getMatchmakerMatchClient(
            final GetMatchmakerMatchClientRequest request) {
        return matchmakerService.getMatchmakerMatchClient(request);
    }

    @Override
    public Uni<FindMatchmakerMatchClientResponse> findMatchmakerMatchClient(
            final FindMatchmakerMatchClientRequest request) {
        return matchmakerService.findMatchmakerMatchClient(request);
    }

    @Override
    public Uni<ViewMatchmakerMatchClientsResponse> viewMatchmakerMatchClients(
            ViewMatchmakerMatchClientsRequest request) {
        return matchmakerService.viewMatchmakerMatchClients(request);
    }

    @Override
    public Uni<SyncMatchmakerMatchClientResponse> syncMatchmakerMatchClient(
            final SyncMatchmakerMatchClientRequest request) {
        return matchmakerService.syncMatchmakerMatchClient(request);
    }

    @Override
    public Uni<DeleteMatchmakerMatchClientResponse> deleteMatchmakerMatchClient(
            final DeleteMatchmakerMatchClientRequest request) {
        return matchmakerService.deleteMatchmakerMatchClient(request);
    }

    @Override
    public Uni<GetMatchmakerMatchRuntimeRefResponse> getMatchmakerMatchRuntimeRef(
            final GetMatchmakerMatchRuntimeRefRequest request) {
        return matchmakerService.getMatchmakerMatchRuntimeRef(request);
    }

    @Override
    public Uni<FindMatchmakerMatchRuntimeRefResponse> findMatchmakerMatchRuntimeRef(
            final FindMatchmakerMatchRuntimeRefRequest request) {
        return matchmakerService.findMatchmakerMatchRuntimeRef(request);
    }

    @Override
    public Uni<SyncMatchmakerMatchRuntimeRefResponse> syncMatchmakerMatchRuntimeRef(
            final SyncMatchmakerMatchRuntimeRefRequest request) {
        return matchmakerService.syncMatchmakerMatchRuntimeRef(request);
    }

    @Override
    public Uni<DeleteMatchmakerMatchRuntimeRefResponse> deleteMatchmakerMatchRuntimeRef(
            final DeleteMatchmakerMatchRuntimeRefRequest request) {
        return matchmakerService.deleteMatchmakerMatchRuntimeRef(request);
    }
}
