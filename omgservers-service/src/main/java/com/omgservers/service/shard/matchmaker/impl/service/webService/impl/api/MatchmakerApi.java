package com.omgservers.service.shard.matchmaker.impl.service.webService.impl.api;

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
import com.omgservers.schema.module.matchmaker.matchmakerMatchResource.UpdateMatchmakerMatchResourceStatusRequest;
import com.omgservers.schema.module.matchmaker.matchmakerMatchResource.UpdateMatchmakerMatchResourceStatusResponse;
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
import com.omgservers.schema.module.matchmaker.matchmakerState.UpdateMatchmakerStateRequest;
import com.omgservers.schema.module.matchmaker.matchmakerState.UpdateMatchmakerStateResponse;
import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

@Tag(name = "Matchmaker Shard API")
@Path("/service/v1/shard/matchmaker/request")
public interface MatchmakerApi {

    /*
    Matchmaker
     */

    @POST
    @Path("/get-matchmaker")
    Uni<GetMatchmakerResponse> execute(GetMatchmakerRequest request);

    @POST
    @Path("/sync-matchmaker")
    Uni<SyncMatchmakerResponse> execute(SyncMatchmakerRequest request);

    @POST
    @Path("/delete-matchmaker")
    Uni<DeleteMatchmakerResponse> execute(DeleteMatchmakerRequest request);

    /*
    MatchmakerCommand
     */

    @POST
    @Path("/view-matchmaker-commands")
    Uni<ViewMatchmakerCommandsResponse> execute(ViewMatchmakerCommandsRequest request);

    @POST
    @Path("/sync-matchmaker-command")
    Uni<SyncMatchmakerCommandResponse> execute(SyncMatchmakerCommandRequest request);

    @POST
    @Path("/delete-matchmaker-command")
    Uni<DeleteMatchmakerCommandResponse> execute(DeleteMatchmakerCommandRequest request);

    /*
    MatchmakerRequest
     */

    @POST
    @Path("/view-matchmaker-requests")
    Uni<ViewMatchmakerRequestsResponse> execute(ViewMatchmakerRequestsRequest request);

    @POST
    @Path("/sync-matchmaker-request")
    Uni<SyncMatchmakerRequestResponse> execute(SyncMatchmakerRequestRequest request);

    @POST
    @Path("/delete-matchmaker-request")
    Uni<DeleteMatchmakerRequestResponse> execute(DeleteMatchmakerRequestRequest request);

    /*
    MatchmakerMatchResource
     */

    @POST
    @Path("/get-matchmaker-match-resource")
    Uni<GetMatchmakerMatchResourceResponse> execute(GetMatchmakerMatchResourceRequest request);

    @POST
    @Path("/view-matchmaker-match-resources")
    Uni<ViewMatchmakerMatchResourcesResponse> execute(ViewMatchmakerMatchResourcesRequest request);

    @POST
    @Path("/sync-matchmaker-match-resource")
    Uni<SyncMatchmakerMatchResourceResponse> execute(SyncMatchmakerMatchResourceRequest request);

    @POST
    @Path("/update-matchmaker-match-resource-status")
    Uni<UpdateMatchmakerMatchResourceStatusResponse> execute(UpdateMatchmakerMatchResourceStatusRequest request);

    @POST
    @Path("/delete-matchmaker-match-resource")
    Uni<DeleteMatchmakerMatchResourceResponse> execute(DeleteMatchmakerMatchResourceRequest request);

    /*
    MatchmakerMatchAssignment
     */

    @POST
    @Path("/get-matchmaker-match-assignment")
    Uni<GetMatchmakerMatchAssignmentResponse> execute(GetMatchmakerMatchAssignmentRequest request);

    @POST
    @Path("/find-matchmaker-match-assignment")
    Uni<FindMatchmakerMatchAssignmentResponse> execute(FindMatchmakerMatchAssignmentRequest request);

    @POST
    @Path("/view-matchmaker-match-assignments")
    Uni<ViewMatchmakerMatchAssignmentsResponse> execute(ViewMatchmakerMatchAssignmentsRequest request);

    @POST
    @Path("/sync-matchmaker-match-assignment")
    Uni<SyncMatchmakerMatchAssignmentResponse> execute(SyncMatchmakerMatchAssignmentRequest request);

    @POST
    @Path("/delete-matchmaker-match-assignment")
    Uni<DeleteMatchmakerMatchAssignmentResponse> execute(DeleteMatchmakerMatchAssignmentRequest request);

    /*
    MatchmakerState
     */

    @POST
    @Path("/get-matchmaker-state")
    Uni<GetMatchmakerStateResponse> execute(GetMatchmakerStateRequest request);

    @POST
    @Path("/update-matchmaker-state")
    Uni<UpdateMatchmakerStateResponse> execute(UpdateMatchmakerStateRequest request);
}
