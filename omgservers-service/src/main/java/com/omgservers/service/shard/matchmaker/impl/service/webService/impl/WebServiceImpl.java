package com.omgservers.service.shard.matchmaker.impl.service.webService.impl;

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
import com.omgservers.schema.shard.matchmaker.matchmakerMatchResource.UpdateMatchmakerMatchResourceStatusRequest;
import com.omgservers.schema.shard.matchmaker.matchmakerMatchResource.UpdateMatchmakerMatchResourceStatusResponse;
import com.omgservers.schema.shard.matchmaker.matchmakerState.UpdateMatchmakerStateRequest;
import com.omgservers.schema.shard.matchmaker.matchmakerState.UpdateMatchmakerStateResponse;
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
    MatchmakerMatchResource
     */

    @Override
    public Uni<GetMatchmakerMatchResourceResponse> execute(final GetMatchmakerMatchResourceRequest request) {
        return matchmakerService.execute(request);
    }

    @Override
    public Uni<ViewMatchmakerMatchResourcesResponse> execute(final ViewMatchmakerMatchResourcesRequest request) {
        return matchmakerService.execute(request);
    }

    @Override
    public Uni<SyncMatchmakerMatchResourceResponse> execute(final SyncMatchmakerMatchResourceRequest request) {
        return matchmakerService.execute(request);
    }

    @Override
    public Uni<UpdateMatchmakerMatchResourceStatusResponse> execute(final UpdateMatchmakerMatchResourceStatusRequest request) {
        return matchmakerService.execute(request);
    }

    @Override
    public Uni<DeleteMatchmakerMatchResourceResponse> execute(final DeleteMatchmakerMatchResourceRequest request) {
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
