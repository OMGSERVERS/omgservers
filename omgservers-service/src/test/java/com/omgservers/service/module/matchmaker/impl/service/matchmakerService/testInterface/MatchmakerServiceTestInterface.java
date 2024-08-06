package com.omgservers.service.module.matchmaker.impl.service.matchmakerService.testInterface;

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
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class MatchmakerServiceTestInterface {
    private static final long TIMEOUT = 1L;

    final MatchmakerService matchmakerService;

    public SyncMatchmakerResponse syncMatchmaker(final SyncMatchmakerRequest request) {
        return matchmakerService.syncMatchmaker(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public GetMatchmakerResponse getMatchmaker(final GetMatchmakerRequest request) {
        return matchmakerService.getMatchmaker(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public DeleteMatchmakerResponse deleteMatchmaker(final DeleteMatchmakerRequest request) {
        return matchmakerService.deleteMatchmaker(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public GetMatchmakerAssignmentResponse getMatchmakerAssignment(final GetMatchmakerAssignmentRequest request) {
        return matchmakerService.getMatchmakerAssignment(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public FindMatchmakerAssignmentResponse findMatchmakerAssignment(final FindMatchmakerAssignmentRequest request) {
        return matchmakerService.findMatchmakerAssignment(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public ViewMatchmakerAssignmentsResponse viewMatchmakerAssignments(final ViewMatchmakerAssignmentsRequest request) {
        return matchmakerService.viewMatchmakerAssignments(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public SyncMatchmakerAssignmentResponse syncMatchmakerAssignment(final SyncMatchmakerAssignmentRequest request) {
        return matchmakerService.syncMatchmakerAssignment(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public DeleteMatchmakerAssignmentResponse deleteMatchmakerAssignment(
            final DeleteMatchmakerAssignmentRequest request) {
        return matchmakerService.deleteMatchmakerAssignment(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public GetMatchmakerStateResponse getMatchmakerState(final GetMatchmakerStateRequest request) {
        return matchmakerService.getMatchmakerState(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public UpdateMatchmakerStateResponse updateMatchmakerState(final UpdateMatchmakerStateRequest request) {
        return matchmakerService.updateMatchmakerState(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public SyncMatchmakerCommandResponse syncMatchmakerCommand(final SyncMatchmakerCommandRequest request) {
        return matchmakerService.syncMatchmakerCommand(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public DeleteMatchmakerCommandResponse deleteMatchmakerCommand(final DeleteMatchmakerCommandRequest request) {
        return matchmakerService.deleteMatchmakerCommand(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public ViewMatchmakerCommandsResponse viewMatchmakerCommands(final ViewMatchmakerCommandsRequest request) {
        return matchmakerService.viewMatchmakerCommands(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public SyncMatchmakerRequestResponse syncMatchmakerRequest(final SyncMatchmakerRequestRequest request) {
        return matchmakerService.syncMatchmakerRequest(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public DeleteMatchmakerRequestResponse deleteMatchmakerRequest(final DeleteMatchmakerRequestRequest request) {
        return matchmakerService.deleteMatchmakerRequest(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public ViewMatchmakerRequestsResponse viewMatchmakerRequests(final ViewMatchmakerRequestsRequest request) {
        return matchmakerService.viewMatchmakerRequests(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public GetMatchmakerMatchResponse getMatchmakerMatch(final GetMatchmakerMatchRequest request) {
        return matchmakerService.getMatchmakerMatch(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public UpdateMatchmakerMatchStatusResponse updateMatchmakerMatchStatus(
            final UpdateMatchmakerMatchStatusRequest request) {
        return matchmakerService.updateMatchmakerMatchStatus(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public ViewMatchmakerMatchesResponse viewMatchmakerMatches(final ViewMatchmakerMatchesRequest request) {
        return matchmakerService.viewMatchmakerMatches(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public SyncMatchmakerMatchResponse syncMatchmakerMatch(final SyncMatchmakerMatchRequest request) {
        return matchmakerService.syncMatchmakerMatch(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public DeleteMatchmakerMatchResponse deleteMatchmakerMatch(final DeleteMatchmakerMatchRequest request) {
        return matchmakerService.deleteMatchmakerMatch(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public GetMatchmakerMatchClientResponse getMatchmakerMatchClient(final GetMatchmakerMatchClientRequest request) {
        return matchmakerService.getMatchmakerMatchClient(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public FindMatchmakerMatchClientResponse findMatchmakerMatchClient(final FindMatchmakerMatchClientRequest request) {
        return matchmakerService.findMatchmakerMatchClient(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public ViewMatchmakerMatchClientsResponse viewMatchmakerMatchClients(
            final ViewMatchmakerMatchClientsRequest request) {
        return matchmakerService.viewMatchmakerMatchClients(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public SyncMatchmakerMatchClientResponse syncMatchmakerMatchClient(final SyncMatchmakerMatchClientRequest request) {
        return matchmakerService.syncMatchmakerMatchClient(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public DeleteMatchmakerMatchClientResponse deleteMatchmakerMatchClient(
            final DeleteMatchmakerMatchClientRequest request) {
        return matchmakerService.deleteMatchmakerMatchClient(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public GetMatchmakerMatchRuntimeRefResponse getMatchmakerMatchRuntimeRef(
            final GetMatchmakerMatchRuntimeRefRequest request) {
        return matchmakerService.getMatchmakerMatchRuntimeRef(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public FindMatchmakerMatchRuntimeRefResponse findMatchmakerMatchRuntimeRef(
            final FindMatchmakerMatchRuntimeRefRequest request) {
        return matchmakerService.findMatchmakerMatchRuntimeRef(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public SyncMatchmakerMatchRuntimeRefResponse syncMatchmakerMatchRuntimeRef(
            final SyncMatchmakerMatchRuntimeRefRequest request) {
        return matchmakerService.syncMatchmakerMatchRuntimeRef(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public DeleteMatchmakerMatchRuntimeRefResponse deleteMatchmakerMatchRuntimeRef(
            final DeleteMatchmakerMatchRuntimeRefRequest request) {
        return matchmakerService.deleteMatchmakerMatchRuntimeRef(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }
}
