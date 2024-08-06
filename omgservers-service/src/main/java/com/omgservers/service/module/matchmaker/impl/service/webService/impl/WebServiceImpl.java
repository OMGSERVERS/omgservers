package com.omgservers.service.module.matchmaker.impl.service.webService.impl;

import com.omgservers.schema.module.matchmaker.DeleteMatchmakerAssignmentRequest;
import com.omgservers.schema.module.matchmaker.DeleteMatchmakerAssignmentResponse;
import com.omgservers.schema.module.matchmaker.DeleteMatchmakerCommandRequest;
import com.omgservers.schema.module.matchmaker.DeleteMatchmakerCommandResponse;
import com.omgservers.schema.module.matchmaker.DeleteMatchmakerMatchClientRequest;
import com.omgservers.schema.module.matchmaker.DeleteMatchmakerMatchClientResponse;
import com.omgservers.schema.module.matchmaker.DeleteMatchmakerMatchRequest;
import com.omgservers.schema.module.matchmaker.DeleteMatchmakerMatchResponse;
import com.omgservers.schema.module.matchmaker.DeleteMatchmakerMatchRuntimeRefRequest;
import com.omgservers.schema.module.matchmaker.DeleteMatchmakerMatchRuntimeRefResponse;
import com.omgservers.schema.module.matchmaker.DeleteMatchmakerRequest;
import com.omgservers.schema.module.matchmaker.DeleteMatchmakerRequestRequest;
import com.omgservers.schema.module.matchmaker.DeleteMatchmakerRequestResponse;
import com.omgservers.schema.module.matchmaker.DeleteMatchmakerResponse;
import com.omgservers.schema.module.matchmaker.FindMatchmakerAssignmentRequest;
import com.omgservers.schema.module.matchmaker.FindMatchmakerAssignmentResponse;
import com.omgservers.schema.module.matchmaker.FindMatchmakerMatchClientRequest;
import com.omgservers.schema.module.matchmaker.FindMatchmakerMatchClientResponse;
import com.omgservers.schema.module.matchmaker.FindMatchmakerMatchRuntimeRefRequest;
import com.omgservers.schema.module.matchmaker.FindMatchmakerMatchRuntimeRefResponse;
import com.omgservers.schema.module.matchmaker.GetMatchmakerAssignmentRequest;
import com.omgservers.schema.module.matchmaker.GetMatchmakerAssignmentResponse;
import com.omgservers.schema.module.matchmaker.GetMatchmakerMatchClientRequest;
import com.omgservers.schema.module.matchmaker.GetMatchmakerMatchClientResponse;
import com.omgservers.schema.module.matchmaker.GetMatchmakerMatchRequest;
import com.omgservers.schema.module.matchmaker.GetMatchmakerMatchResponse;
import com.omgservers.schema.module.matchmaker.GetMatchmakerMatchRuntimeRefRequest;
import com.omgservers.schema.module.matchmaker.GetMatchmakerMatchRuntimeRefResponse;
import com.omgservers.schema.module.matchmaker.GetMatchmakerRequest;
import com.omgservers.schema.module.matchmaker.GetMatchmakerResponse;
import com.omgservers.schema.module.matchmaker.GetMatchmakerStateRequest;
import com.omgservers.schema.module.matchmaker.GetMatchmakerStateResponse;
import com.omgservers.schema.module.matchmaker.SyncMatchmakerAssignmentRequest;
import com.omgservers.schema.module.matchmaker.SyncMatchmakerAssignmentResponse;
import com.omgservers.schema.module.matchmaker.SyncMatchmakerCommandRequest;
import com.omgservers.schema.module.matchmaker.SyncMatchmakerCommandResponse;
import com.omgservers.schema.module.matchmaker.SyncMatchmakerMatchClientRequest;
import com.omgservers.schema.module.matchmaker.SyncMatchmakerMatchClientResponse;
import com.omgservers.schema.module.matchmaker.SyncMatchmakerMatchRequest;
import com.omgservers.schema.module.matchmaker.SyncMatchmakerMatchResponse;
import com.omgservers.schema.module.matchmaker.SyncMatchmakerMatchRuntimeRefRequest;
import com.omgservers.schema.module.matchmaker.SyncMatchmakerMatchRuntimeRefResponse;
import com.omgservers.schema.module.matchmaker.SyncMatchmakerRequest;
import com.omgservers.schema.module.matchmaker.SyncMatchmakerRequestRequest;
import com.omgservers.schema.module.matchmaker.SyncMatchmakerRequestResponse;
import com.omgservers.schema.module.matchmaker.SyncMatchmakerResponse;
import com.omgservers.schema.module.matchmaker.UpdateMatchmakerMatchStatusRequest;
import com.omgservers.schema.module.matchmaker.UpdateMatchmakerMatchStatusResponse;
import com.omgservers.schema.module.matchmaker.UpdateMatchmakerStateRequest;
import com.omgservers.schema.module.matchmaker.UpdateMatchmakerStateResponse;
import com.omgservers.schema.module.matchmaker.ViewMatchmakerAssignmentsRequest;
import com.omgservers.schema.module.matchmaker.ViewMatchmakerAssignmentsResponse;
import com.omgservers.schema.module.matchmaker.ViewMatchmakerCommandsRequest;
import com.omgservers.schema.module.matchmaker.ViewMatchmakerCommandsResponse;
import com.omgservers.schema.module.matchmaker.ViewMatchmakerMatchClientsRequest;
import com.omgservers.schema.module.matchmaker.ViewMatchmakerMatchClientsResponse;
import com.omgservers.schema.module.matchmaker.ViewMatchmakerMatchesRequest;
import com.omgservers.schema.module.matchmaker.ViewMatchmakerMatchesResponse;
import com.omgservers.schema.module.matchmaker.ViewMatchmakerRequestsRequest;
import com.omgservers.schema.module.matchmaker.ViewMatchmakerRequestsResponse;
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
    public Uni<GetMatchmakerAssignmentResponse> getMatchmakerAssignment(final GetMatchmakerAssignmentRequest request) {
        return matchmakerService.getMatchmakerAssignment(request);
    }

    @Override
    public Uni<FindMatchmakerAssignmentResponse> findMatchmakerAssignment(final FindMatchmakerAssignmentRequest request) {
        return matchmakerService.findMatchmakerAssignment(request);
    }

    @Override
    public Uni<ViewMatchmakerAssignmentsResponse> viewMatchmakerAssignments(final ViewMatchmakerAssignmentsRequest request) {
        return matchmakerService.viewMatchmakerAssignments(request);
    }

    @Override
    public Uni<SyncMatchmakerAssignmentResponse> syncMatchmakerAssignment(final SyncMatchmakerAssignmentRequest request) {
        return matchmakerService.syncMatchmakerAssignment(request);
    }

    @Override
    public Uni<DeleteMatchmakerAssignmentResponse> deleteMatchmakerAssignment(
            final DeleteMatchmakerAssignmentRequest request) {
        return matchmakerService.deleteMatchmakerAssignment(request);
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
