package com.omgservers.service.module.matchmaker.impl.service.matchmakerService.testInterface;

import com.omgservers.model.dto.matchmaker.DeleteMatchmakerAssignmentRequest;
import com.omgservers.model.dto.matchmaker.DeleteMatchmakerAssignmentResponse;
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
import com.omgservers.model.dto.matchmaker.FindMatchmakerAssignmentRequest;
import com.omgservers.model.dto.matchmaker.FindMatchmakerAssignmentResponse;
import com.omgservers.model.dto.matchmaker.FindMatchmakerMatchClientRequest;
import com.omgservers.model.dto.matchmaker.FindMatchmakerMatchClientResponse;
import com.omgservers.model.dto.matchmaker.FindMatchmakerMatchRuntimeRefRequest;
import com.omgservers.model.dto.matchmaker.FindMatchmakerMatchRuntimeRefResponse;
import com.omgservers.model.dto.matchmaker.GetMatchmakerAssignmentRequest;
import com.omgservers.model.dto.matchmaker.GetMatchmakerAssignmentResponse;
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
import com.omgservers.model.dto.matchmaker.SyncMatchmakerAssignmentRequest;
import com.omgservers.model.dto.matchmaker.SyncMatchmakerAssignmentResponse;
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
import com.omgservers.model.dto.matchmaker.ViewMatchmakerAssignmentsRequest;
import com.omgservers.model.dto.matchmaker.ViewMatchmakerAssignmentsResponse;
import com.omgservers.model.dto.matchmaker.ViewMatchmakerCommandsRequest;
import com.omgservers.model.dto.matchmaker.ViewMatchmakerCommandsResponse;
import com.omgservers.model.dto.matchmaker.ViewMatchmakerMatchClientsRequest;
import com.omgservers.model.dto.matchmaker.ViewMatchmakerMatchClientsResponse;
import com.omgservers.model.dto.matchmaker.ViewMatchmakerMatchesRequest;
import com.omgservers.model.dto.matchmaker.ViewMatchmakerMatchesResponse;
import com.omgservers.model.dto.matchmaker.ViewMatchmakerRequestsRequest;
import com.omgservers.model.dto.matchmaker.ViewMatchmakerRequestsResponse;
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
