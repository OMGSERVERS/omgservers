package com.omgservers.service.shard.matchmaker.service.testInterface;

import com.omgservers.schema.shard.matchmaker.matchmaker.DeleteMatchmakerRequest;
import com.omgservers.schema.shard.matchmaker.matchmaker.DeleteMatchmakerResponse;
import com.omgservers.schema.shard.matchmaker.matchmaker.GetMatchmakerRequest;
import com.omgservers.schema.shard.matchmaker.matchmaker.GetMatchmakerResponse;
import com.omgservers.schema.shard.matchmaker.matchmaker.SyncMatchmakerRequest;
import com.omgservers.schema.shard.matchmaker.matchmaker.SyncMatchmakerResponse;
import com.omgservers.schema.shard.matchmaker.matchmakerCommand.DeleteMatchmakerCommandRequest;
import com.omgservers.schema.shard.matchmaker.matchmakerCommand.DeleteMatchmakerCommandResponse;
import com.omgservers.schema.shard.matchmaker.matchmakerCommand.SyncMatchmakerCommandRequest;
import com.omgservers.schema.shard.matchmaker.matchmakerCommand.SyncMatchmakerCommandResponse;
import com.omgservers.schema.shard.matchmaker.matchmakerCommand.ViewMatchmakerCommandsRequest;
import com.omgservers.schema.shard.matchmaker.matchmakerCommand.ViewMatchmakerCommandsResponse;
import com.omgservers.schema.shard.matchmaker.matchmakerMatchAssignment.DeleteMatchmakerMatchAssignmentRequest;
import com.omgservers.schema.shard.matchmaker.matchmakerMatchAssignment.DeleteMatchmakerMatchAssignmentResponse;
import com.omgservers.schema.shard.matchmaker.matchmakerMatchAssignment.FindMatchmakerMatchAssignmentRequest;
import com.omgservers.schema.shard.matchmaker.matchmakerMatchAssignment.FindMatchmakerMatchAssignmentResponse;
import com.omgservers.schema.shard.matchmaker.matchmakerMatchAssignment.GetMatchmakerMatchAssignmentRequest;
import com.omgservers.schema.shard.matchmaker.matchmakerMatchAssignment.GetMatchmakerMatchAssignmentResponse;
import com.omgservers.schema.shard.matchmaker.matchmakerMatchAssignment.SyncMatchmakerMatchAssignmentRequest;
import com.omgservers.schema.shard.matchmaker.matchmakerMatchAssignment.SyncMatchmakerMatchAssignmentResponse;
import com.omgservers.schema.shard.matchmaker.matchmakerMatchAssignment.ViewMatchmakerMatchAssignmentsRequest;
import com.omgservers.schema.shard.matchmaker.matchmakerMatchAssignment.ViewMatchmakerMatchAssignmentsResponse;
import com.omgservers.schema.shard.matchmaker.matchmakerMatchResource.DeleteMatchmakerMatchResourceRequest;
import com.omgservers.schema.shard.matchmaker.matchmakerMatchResource.DeleteMatchmakerMatchResourceResponse;
import com.omgservers.schema.shard.matchmaker.matchmakerMatchResource.GetMatchmakerMatchResourceRequest;
import com.omgservers.schema.shard.matchmaker.matchmakerMatchResource.GetMatchmakerMatchResourceResponse;
import com.omgservers.schema.shard.matchmaker.matchmakerMatchResource.SyncMatchmakerMatchResourceRequest;
import com.omgservers.schema.shard.matchmaker.matchmakerMatchResource.SyncMatchmakerMatchResourceResponse;
import com.omgservers.schema.shard.matchmaker.matchmakerMatchResource.UpdateMatchmakerMatchResourceStatusRequest;
import com.omgservers.schema.shard.matchmaker.matchmakerMatchResource.UpdateMatchmakerMatchResourceStatusResponse;
import com.omgservers.schema.shard.matchmaker.matchmakerMatchResource.ViewMatchmakerMatchResourcesRequest;
import com.omgservers.schema.shard.matchmaker.matchmakerMatchResource.ViewMatchmakerMatchResourcesResponse;
import com.omgservers.schema.shard.matchmaker.matchmakerRequest.DeleteMatchmakerRequestRequest;
import com.omgservers.schema.shard.matchmaker.matchmakerRequest.DeleteMatchmakerRequestResponse;
import com.omgservers.schema.shard.matchmaker.matchmakerRequest.SyncMatchmakerRequestRequest;
import com.omgservers.schema.shard.matchmaker.matchmakerRequest.SyncMatchmakerRequestResponse;
import com.omgservers.schema.shard.matchmaker.matchmakerRequest.ViewMatchmakerRequestsRequest;
import com.omgservers.schema.shard.matchmaker.matchmakerRequest.ViewMatchmakerRequestsResponse;
import com.omgservers.schema.shard.matchmaker.matchmakerState.GetMatchmakerStateRequest;
import com.omgservers.schema.shard.matchmaker.matchmakerState.GetMatchmakerStateResponse;
import com.omgservers.schema.shard.matchmaker.matchmakerState.UpdateMatchmakerStateRequest;
import com.omgservers.schema.shard.matchmaker.matchmakerState.UpdateMatchmakerStateResponse;
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

    public GetMatchmakerMatchResourceResponse execute(final GetMatchmakerMatchResourceRequest request) {
        return matchmakerService.execute(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public UpdateMatchmakerMatchResourceStatusResponse execute(final UpdateMatchmakerMatchResourceStatusRequest request) {
        return matchmakerService.execute(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public ViewMatchmakerMatchResourcesResponse execute(final ViewMatchmakerMatchResourcesRequest request) {
        return matchmakerService.execute(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public SyncMatchmakerMatchResourceResponse execute(final SyncMatchmakerMatchResourceRequest request) {
        return matchmakerService.execute(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public DeleteMatchmakerMatchResourceResponse execute(final DeleteMatchmakerMatchResourceRequest request) {
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
}
