package com.omgservers.service.shard.matchmaker.impl.service.webService;

import com.omgservers.schema.module.matchmaker.matchmaker.DeleteMatchmakerRequest;
import com.omgservers.schema.module.matchmaker.matchmaker.DeleteMatchmakerResponse;
import com.omgservers.schema.module.matchmaker.matchmaker.GetMatchmakerRequest;
import com.omgservers.schema.module.matchmaker.matchmaker.GetMatchmakerResponse;
import com.omgservers.schema.module.matchmaker.matchmaker.SyncMatchmakerRequest;
import com.omgservers.schema.module.matchmaker.matchmaker.SyncMatchmakerResponse;
import com.omgservers.schema.module.matchmaker.matchmakerCommand.DeleteMatchmakerCommandRequest;
import com.omgservers.schema.module.matchmaker.matchmakerCommand.DeleteMatchmakerCommandResponse;
import com.omgservers.schema.module.matchmaker.matchmakerCommand.SyncMatchmakerCommandRequest;
import com.omgservers.schema.module.matchmaker.matchmakerCommand.SyncMatchmakerCommandResponse;
import com.omgservers.schema.module.matchmaker.matchmakerCommand.ViewMatchmakerCommandsRequest;
import com.omgservers.schema.module.matchmaker.matchmakerCommand.ViewMatchmakerCommandsResponse;
import com.omgservers.schema.module.matchmaker.matchmakerMatchAssignment.DeleteMatchmakerMatchAssignmentRequest;
import com.omgservers.schema.module.matchmaker.matchmakerMatchAssignment.DeleteMatchmakerMatchAssignmentResponse;
import com.omgservers.schema.module.matchmaker.matchmakerMatchAssignment.FindMatchmakerMatchAssignmentRequest;
import com.omgservers.schema.module.matchmaker.matchmakerMatchAssignment.FindMatchmakerMatchAssignmentResponse;
import com.omgservers.schema.module.matchmaker.matchmakerMatchAssignment.GetMatchmakerMatchAssignmentRequest;
import com.omgservers.schema.module.matchmaker.matchmakerMatchAssignment.GetMatchmakerMatchAssignmentResponse;
import com.omgservers.schema.module.matchmaker.matchmakerMatchAssignment.SyncMatchmakerMatchAssignmentRequest;
import com.omgservers.schema.module.matchmaker.matchmakerMatchAssignment.SyncMatchmakerMatchAssignmentResponse;
import com.omgservers.schema.module.matchmaker.matchmakerMatchAssignment.ViewMatchmakerMatchAssignmentsRequest;
import com.omgservers.schema.module.matchmaker.matchmakerMatchAssignment.ViewMatchmakerMatchAssignmentsResponse;
import com.omgservers.schema.module.matchmaker.matchmakerMatchResource.DeleteMatchmakerMatchResourceRequest;
import com.omgservers.schema.module.matchmaker.matchmakerMatchResource.DeleteMatchmakerMatchResourceResponse;
import com.omgservers.schema.module.matchmaker.matchmakerMatchResource.GetMatchmakerMatchResourceRequest;
import com.omgservers.schema.module.matchmaker.matchmakerMatchResource.GetMatchmakerMatchResourceResponse;
import com.omgservers.schema.module.matchmaker.matchmakerMatchResource.SyncMatchmakerMatchResourceRequest;
import com.omgservers.schema.module.matchmaker.matchmakerMatchResource.SyncMatchmakerMatchResourceResponse;
import com.omgservers.schema.module.matchmaker.matchmakerMatchResource.ViewMatchmakerMatchResourcesRequest;
import com.omgservers.schema.module.matchmaker.matchmakerMatchResource.ViewMatchmakerMatchResourcesResponse;
import com.omgservers.schema.module.matchmaker.matchmakerRequest.DeleteMatchmakerRequestRequest;
import com.omgservers.schema.module.matchmaker.matchmakerRequest.DeleteMatchmakerRequestResponse;
import com.omgservers.schema.module.matchmaker.matchmakerRequest.SyncMatchmakerRequestRequest;
import com.omgservers.schema.module.matchmaker.matchmakerRequest.SyncMatchmakerRequestResponse;
import com.omgservers.schema.module.matchmaker.matchmakerRequest.ViewMatchmakerRequestsRequest;
import com.omgservers.schema.module.matchmaker.matchmakerRequest.ViewMatchmakerRequestsResponse;
import com.omgservers.schema.module.matchmaker.matchmakerState.GetMatchmakerStateRequest;
import com.omgservers.schema.module.matchmaker.matchmakerState.GetMatchmakerStateResponse;
import com.omgservers.schema.module.matchmaker.matchmakerMatchResource.UpdateMatchmakerMatchResourceStatusRequest;
import com.omgservers.schema.module.matchmaker.matchmakerMatchResource.UpdateMatchmakerMatchResourceStatusResponse;
import com.omgservers.schema.module.matchmaker.matchmakerState.UpdateMatchmakerStateRequest;
import com.omgservers.schema.module.matchmaker.matchmakerState.UpdateMatchmakerStateResponse;
import io.smallrye.mutiny.Uni;

public interface WebService {

    /*
    Matchmaker
     */

    Uni<SyncMatchmakerResponse> execute(SyncMatchmakerRequest request);

    Uni<GetMatchmakerResponse> execute(GetMatchmakerRequest request);

    Uni<DeleteMatchmakerResponse> execute(DeleteMatchmakerRequest request);

    /*
    MatchmakerCommand
     */

    Uni<ViewMatchmakerCommandsResponse> execute(ViewMatchmakerCommandsRequest request);

    Uni<SyncMatchmakerCommandResponse> execute(SyncMatchmakerCommandRequest request);

    Uni<DeleteMatchmakerCommandResponse> execute(DeleteMatchmakerCommandRequest request);

    /*
    MatchmakerRequest
     */

    Uni<ViewMatchmakerRequestsResponse> execute(ViewMatchmakerRequestsRequest request);

    Uni<SyncMatchmakerRequestResponse> execute(SyncMatchmakerRequestRequest request);

    Uni<DeleteMatchmakerRequestResponse> execute(DeleteMatchmakerRequestRequest request);

    /*
    MatcherMatchResources
     */

    Uni<GetMatchmakerMatchResourceResponse> execute(GetMatchmakerMatchResourceRequest request);

    Uni<ViewMatchmakerMatchResourcesResponse> execute(ViewMatchmakerMatchResourcesRequest request);

    Uni<SyncMatchmakerMatchResourceResponse> execute(SyncMatchmakerMatchResourceRequest request);

    Uni<UpdateMatchmakerMatchResourceStatusResponse> execute(UpdateMatchmakerMatchResourceStatusRequest request);

    Uni<DeleteMatchmakerMatchResourceResponse> execute(DeleteMatchmakerMatchResourceRequest request);

    /*
    MatchmakerMatchAssignment
     */

    Uni<GetMatchmakerMatchAssignmentResponse> execute(GetMatchmakerMatchAssignmentRequest request);

    Uni<FindMatchmakerMatchAssignmentResponse> execute(FindMatchmakerMatchAssignmentRequest request);

    Uni<ViewMatchmakerMatchAssignmentsResponse> execute(ViewMatchmakerMatchAssignmentsRequest request);

    Uni<SyncMatchmakerMatchAssignmentResponse> execute(SyncMatchmakerMatchAssignmentRequest request);

    Uni<DeleteMatchmakerMatchAssignmentResponse> execute(DeleteMatchmakerMatchAssignmentRequest request);

    /*
    MatchmakerState
     */

    Uni<GetMatchmakerStateResponse> execute(GetMatchmakerStateRequest request);

    Uni<UpdateMatchmakerStateResponse> execute(UpdateMatchmakerStateRequest request);
}
