package com.omgservers.service.shard.matchmaker.impl.service.webService.impl;

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
import com.omgservers.service.shard.matchmaker.impl.service.webService.WebService;
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

    /*
    Matchmaker
     */

    @Override
    public Uni<GetMatchmakerResponse> execute(final GetMatchmakerRequest request) {
        return matchmakerService.execute(request);
    }

    @Override
    public Uni<SyncMatchmakerResponse> execute(final SyncMatchmakerRequest request) {
        return matchmakerService.execute(request);
    }

    @Override
    public Uni<DeleteMatchmakerResponse> execute(final DeleteMatchmakerRequest request) {
        return matchmakerService.execute(request);
    }

    /*
    MatchmakerAssignment
     */

    @Override
    public Uni<GetMatchmakerAssignmentResponse> execute(final GetMatchmakerAssignmentRequest request) {
        return matchmakerService.execute(request);
    }

    @Override
    public Uni<FindMatchmakerAssignmentResponse> execute(final FindMatchmakerAssignmentRequest request) {
        return matchmakerService.execute(request);
    }

    @Override
    public Uni<ViewMatchmakerAssignmentsResponse> execute(final ViewMatchmakerAssignmentsRequest request) {
        return matchmakerService.execute(request);
    }

    @Override
    public Uni<SyncMatchmakerAssignmentResponse> execute(final SyncMatchmakerAssignmentRequest request) {
        return matchmakerService.execute(request);
    }

    @Override
    public Uni<DeleteMatchmakerAssignmentResponse> execute(final DeleteMatchmakerAssignmentRequest request) {
        return matchmakerService.execute(request);
    }

    /*
    MatchmakerCommand
     */

    @Override
    public Uni<ViewMatchmakerCommandsResponse> execute(final ViewMatchmakerCommandsRequest request) {
        return matchmakerService.execute(request);
    }

    @Override
    public Uni<SyncMatchmakerCommandResponse> execute(final SyncMatchmakerCommandRequest request) {
        return matchmakerService.execute(request);
    }

    @Override
    public Uni<DeleteMatchmakerCommandResponse> execute(final DeleteMatchmakerCommandRequest request) {
        return matchmakerService.execute(request);
    }

    /*
    MatchmakerRequest
     */

    @Override
    public Uni<ViewMatchmakerRequestsResponse> execute(final ViewMatchmakerRequestsRequest request) {
        return matchmakerService.execute(request);
    }

    @Override
    public Uni<SyncMatchmakerRequestResponse> execute(final SyncMatchmakerRequestRequest request) {
        return matchmakerService.execute(request);
    }

    @Override
    public Uni<DeleteMatchmakerRequestResponse> execute(final DeleteMatchmakerRequestRequest request) {
        return matchmakerService.execute(request);
    }

    /*
    MatchmakerMatch
     */

    @Override
    public Uni<GetMatchmakerMatchResponse> execute(final GetMatchmakerMatchRequest request) {
        return matchmakerService.execute(request);
    }

    @Override
    public Uni<ViewMatchmakerMatchesResponse> execute(final ViewMatchmakerMatchesRequest request) {
        return matchmakerService.execute(request);
    }

    @Override
    public Uni<SyncMatchmakerMatchResponse> execute(final SyncMatchmakerMatchRequest request) {
        return matchmakerService.execute(request);
    }

    @Override
    public Uni<UpdateMatchmakerMatchStatusResponse> execute(final UpdateMatchmakerMatchStatusRequest request) {
        return matchmakerService.execute(request);
    }

    @Override
    public Uni<DeleteMatchmakerMatchResponse> execute(final DeleteMatchmakerMatchRequest request) {
        return matchmakerService.execute(request);
    }

    /*
    MatchmakerMatchAssignment
     */

    @Override
    public Uni<GetMatchmakerMatchAssignmentResponse> execute(final GetMatchmakerMatchAssignmentRequest request) {
        return matchmakerService.execute(request);
    }

    @Override
    public Uni<FindMatchmakerMatchAssignmentResponse> execute(final FindMatchmakerMatchAssignmentRequest request) {
        return matchmakerService.execute(request);
    }

    @Override
    public Uni<ViewMatchmakerMatchAssignmentsResponse> execute(ViewMatchmakerMatchAssignmentsRequest request) {
        return matchmakerService.execute(request);
    }

    @Override
    public Uni<SyncMatchmakerMatchAssignmentResponse> execute(final SyncMatchmakerMatchAssignmentRequest request) {
        return matchmakerService.execute(request);
    }

    @Override
    public Uni<DeleteMatchmakerMatchAssignmentResponse> execute(final DeleteMatchmakerMatchAssignmentRequest request) {
        return matchmakerService.execute(request);
    }

    /*
    MatchmakerMatchRuntimeRef
     */

    @Override
    public Uni<GetMatchmakerMatchRuntimeRefResponse> execute(final GetMatchmakerMatchRuntimeRefRequest request) {
        return matchmakerService.execute(request);
    }

    @Override
    public Uni<FindMatchmakerMatchRuntimeRefResponse> execute(final FindMatchmakerMatchRuntimeRefRequest request) {
        return matchmakerService.execute(request);
    }

    @Override
    public Uni<SyncMatchmakerMatchRuntimeRefResponse> execute(final SyncMatchmakerMatchRuntimeRefRequest request) {
        return matchmakerService.execute(request);
    }

    @Override
    public Uni<DeleteMatchmakerMatchRuntimeRefResponse> execute(final DeleteMatchmakerMatchRuntimeRefRequest request) {
        return matchmakerService.execute(request);
    }

    /*
    MatchmakerState
     */

    @Override
    public Uni<GetMatchmakerStateResponse> execute(GetMatchmakerStateRequest request) {
        return matchmakerService.execute(request);
    }

    @Override
    public Uni<UpdateMatchmakerStateResponse> execute(final UpdateMatchmakerStateRequest request) {
        return matchmakerService.execute(request);
    }

}
