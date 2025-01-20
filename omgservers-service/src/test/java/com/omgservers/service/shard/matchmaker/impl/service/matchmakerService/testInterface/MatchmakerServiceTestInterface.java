package com.omgservers.service.shard.matchmaker.impl.service.matchmakerService.testInterface;

import com.omgservers.schema.module.matchmaker.DeleteMatchmakerAssignmentRequest;
import com.omgservers.schema.module.matchmaker.DeleteMatchmakerAssignmentResponse;
import com.omgservers.schema.module.matchmaker.DeleteMatchmakerCommandRequest;
import com.omgservers.schema.module.matchmaker.DeleteMatchmakerCommandResponse;
import com.omgservers.schema.module.matchmaker.DeleteMatchmakerMatchAssignmentRequest;
import com.omgservers.schema.module.matchmaker.DeleteMatchmakerMatchAssignmentResponse;
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
import com.omgservers.schema.module.matchmaker.FindMatchmakerMatchAssignmentRequest;
import com.omgservers.schema.module.matchmaker.FindMatchmakerMatchAssignmentResponse;
import com.omgservers.schema.module.matchmaker.FindMatchmakerMatchRuntimeRefRequest;
import com.omgservers.schema.module.matchmaker.FindMatchmakerMatchRuntimeRefResponse;
import com.omgservers.schema.module.matchmaker.GetMatchmakerAssignmentRequest;
import com.omgservers.schema.module.matchmaker.GetMatchmakerAssignmentResponse;
import com.omgservers.schema.module.matchmaker.GetMatchmakerMatchAssignmentRequest;
import com.omgservers.schema.module.matchmaker.GetMatchmakerMatchAssignmentResponse;
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
import com.omgservers.schema.module.matchmaker.SyncMatchmakerMatchAssignmentRequest;
import com.omgservers.schema.module.matchmaker.SyncMatchmakerMatchAssignmentResponse;
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
import com.omgservers.schema.module.matchmaker.ViewMatchmakerMatchAssignmentsRequest;
import com.omgservers.schema.module.matchmaker.ViewMatchmakerMatchAssignmentsResponse;
import com.omgservers.schema.module.matchmaker.ViewMatchmakerMatchesRequest;
import com.omgservers.schema.module.matchmaker.ViewMatchmakerMatchesResponse;
import com.omgservers.schema.module.matchmaker.ViewMatchmakerRequestsRequest;
import com.omgservers.schema.module.matchmaker.ViewMatchmakerRequestsResponse;
import com.omgservers.service.shard.matchmaker.impl.service.matchmakerService.MatchmakerService;
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

    public SyncMatchmakerResponse execute(final SyncMatchmakerRequest request) {
        return matchmakerService.execute(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public GetMatchmakerResponse execute(final GetMatchmakerRequest request) {
        return matchmakerService.execute(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public DeleteMatchmakerResponse execute(final DeleteMatchmakerRequest request) {
        return matchmakerService.execute(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public GetMatchmakerAssignmentResponse execute(final GetMatchmakerAssignmentRequest request) {
        return matchmakerService.execute(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public FindMatchmakerAssignmentResponse execute(final FindMatchmakerAssignmentRequest request) {
        return matchmakerService.execute(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public ViewMatchmakerAssignmentsResponse execute(final ViewMatchmakerAssignmentsRequest request) {
        return matchmakerService.execute(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public SyncMatchmakerAssignmentResponse execute(final SyncMatchmakerAssignmentRequest request) {
        return matchmakerService.execute(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public DeleteMatchmakerAssignmentResponse execute(final DeleteMatchmakerAssignmentRequest request) {
        return matchmakerService.execute(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public GetMatchmakerStateResponse execute(final GetMatchmakerStateRequest request) {
        return matchmakerService.execute(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public UpdateMatchmakerStateResponse execute(final UpdateMatchmakerStateRequest request) {
        return matchmakerService.execute(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public SyncMatchmakerCommandResponse execute(final SyncMatchmakerCommandRequest request) {
        return matchmakerService.execute(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public DeleteMatchmakerCommandResponse execute(final DeleteMatchmakerCommandRequest request) {
        return matchmakerService.execute(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public ViewMatchmakerCommandsResponse execute(final ViewMatchmakerCommandsRequest request) {
        return matchmakerService.execute(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public SyncMatchmakerRequestResponse execute(final SyncMatchmakerRequestRequest request) {
        return matchmakerService.execute(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public DeleteMatchmakerRequestResponse execute(final DeleteMatchmakerRequestRequest request) {
        return matchmakerService.execute(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public ViewMatchmakerRequestsResponse execute(final ViewMatchmakerRequestsRequest request) {
        return matchmakerService.execute(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public GetMatchmakerMatchResponse execute(final GetMatchmakerMatchRequest request) {
        return matchmakerService.execute(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public UpdateMatchmakerMatchStatusResponse execute(final UpdateMatchmakerMatchStatusRequest request) {
        return matchmakerService.execute(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public ViewMatchmakerMatchesResponse execute(final ViewMatchmakerMatchesRequest request) {
        return matchmakerService.execute(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public SyncMatchmakerMatchResponse execute(final SyncMatchmakerMatchRequest request) {
        return matchmakerService.execute(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public DeleteMatchmakerMatchResponse execute(final DeleteMatchmakerMatchRequest request) {
        return matchmakerService.execute(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public GetMatchmakerMatchAssignmentResponse execute(final GetMatchmakerMatchAssignmentRequest request) {
        return matchmakerService.execute(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public FindMatchmakerMatchAssignmentResponse execute(final FindMatchmakerMatchAssignmentRequest request) {
        return matchmakerService.execute(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public ViewMatchmakerMatchAssignmentsResponse execute(final ViewMatchmakerMatchAssignmentsRequest request) {
        return matchmakerService.execute(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public SyncMatchmakerMatchAssignmentResponse execute(final SyncMatchmakerMatchAssignmentRequest request) {
        return matchmakerService.execute(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public DeleteMatchmakerMatchAssignmentResponse execute(final DeleteMatchmakerMatchAssignmentRequest request) {
        return matchmakerService.execute(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public GetMatchmakerMatchRuntimeRefResponse execute(final GetMatchmakerMatchRuntimeRefRequest request) {
        return matchmakerService.execute(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public FindMatchmakerMatchRuntimeRefResponse execute(final FindMatchmakerMatchRuntimeRefRequest request) {
        return matchmakerService.execute(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public SyncMatchmakerMatchRuntimeRefResponse execute(final SyncMatchmakerMatchRuntimeRefRequest request) {
        return matchmakerService.execute(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public DeleteMatchmakerMatchRuntimeRefResponse execute(final DeleteMatchmakerMatchRuntimeRefRequest request) {
        return matchmakerService.execute(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }
}
