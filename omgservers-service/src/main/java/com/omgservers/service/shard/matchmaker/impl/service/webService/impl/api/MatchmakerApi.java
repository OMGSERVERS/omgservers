package com.omgservers.service.shard.matchmaker.impl.service.webService.impl.api;

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
import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

@Tag(name = "Matchmaker Shard API")
@Path("/service/v1/shard/matchmaker/request")
public interface MatchmakerApi {

    /*
    Matchmaker
     */

    @PUT
    @Path("/get-matchmaker")
    Uni<GetMatchmakerResponse> execute(GetMatchmakerRequest request);

    @PUT
    @Path("/sync-matchmaker")
    Uni<SyncMatchmakerResponse> execute(SyncMatchmakerRequest request);

    @PUT
    @Path("/delete-matchmaker")
    Uni<DeleteMatchmakerResponse> execute(DeleteMatchmakerRequest request);

    /*
    MatchmakerAssignment
     */

    @PUT
    @Path("/get-matchmaker-assignment")
    Uni<GetMatchmakerAssignmentResponse> execute(GetMatchmakerAssignmentRequest request);

    @PUT
    @Path("/find-matchmaker-assignment")
    Uni<FindMatchmakerAssignmentResponse> execute(FindMatchmakerAssignmentRequest request);

    @PUT
    @Path("/view-matchmaker-assignments")
    Uni<ViewMatchmakerAssignmentsResponse> execute(ViewMatchmakerAssignmentsRequest request);

    @PUT
    @Path("/sync-matchmaker-assignment")
    Uni<SyncMatchmakerAssignmentResponse> execute(SyncMatchmakerAssignmentRequest request);

    @PUT
    @Path("/delete-matchmaker-assignment")
    Uni<DeleteMatchmakerAssignmentResponse> execute(DeleteMatchmakerAssignmentRequest request);

    /*
    MatchmakerCommand
     */

    @PUT
    @Path("/view-matchmaker-commands")
    Uni<ViewMatchmakerCommandsResponse> execute(ViewMatchmakerCommandsRequest request);

    @PUT
    @Path("/sync-matchmaker-command")
    Uni<SyncMatchmakerCommandResponse> execute(SyncMatchmakerCommandRequest request);

    @PUT
    @Path("/delete-matchmaker-command")
    Uni<DeleteMatchmakerCommandResponse> execute(DeleteMatchmakerCommandRequest request);

    /*
    MatchmakerRequest
     */

    @PUT
    @Path("/view-matchmaker-requests")
    Uni<ViewMatchmakerRequestsResponse> execute(ViewMatchmakerRequestsRequest request);

    @PUT
    @Path("/sync-matchmaker-request")
    Uni<SyncMatchmakerRequestResponse> execute(SyncMatchmakerRequestRequest request);

    @PUT
    @Path("/delete-matchmaker-request")
    Uni<DeleteMatchmakerRequestResponse> execute(DeleteMatchmakerRequestRequest request);

    /*
    MatchmakerMatch
     */

    @PUT
    @Path("/get-matchmaker-match")
    Uni<GetMatchmakerMatchResponse> execute(GetMatchmakerMatchRequest request);

    @PUT
    @Path("/view-matchmaker-matches")
    Uni<ViewMatchmakerMatchesResponse> execute(ViewMatchmakerMatchesRequest request);

    @PUT
    @Path("/sync-matchmaker-match")
    Uni<SyncMatchmakerMatchResponse> execute(SyncMatchmakerMatchRequest request);

    @PUT
    @Path("/update-matchmaker-match-status")
    Uni<UpdateMatchmakerMatchStatusResponse> execute(UpdateMatchmakerMatchStatusRequest request);

    @PUT
    @Path("/delete-matchmaker-match")
    Uni<DeleteMatchmakerMatchResponse> execute(DeleteMatchmakerMatchRequest request);

    /*
    MatchmakerMatchAssignment
     */

    @PUT
    @Path("/get-matchmaker-match-assignment")
    Uni<GetMatchmakerMatchAssignmentResponse> execute(GetMatchmakerMatchAssignmentRequest request);

    @PUT
    @Path("/find-matchmaker-match-assignment")
    Uni<FindMatchmakerMatchAssignmentResponse> execute(FindMatchmakerMatchAssignmentRequest request);

    @PUT
    @Path("/view-matchmaker-match-assignments")
    Uni<ViewMatchmakerMatchAssignmentsResponse> execute(ViewMatchmakerMatchAssignmentsRequest request);

    @PUT
    @Path("/sync-matchmaker-match-assignment")
    Uni<SyncMatchmakerMatchAssignmentResponse> execute(SyncMatchmakerMatchAssignmentRequest request);

    @PUT
    @Path("/delete-matchmaker-match-assignment")
    Uni<DeleteMatchmakerMatchAssignmentResponse> execute(DeleteMatchmakerMatchAssignmentRequest request);

    /*
    MatchmakerMatchRuntimeRef
     */

    @PUT
    @Path("/get-matchmaker-match-runtime-ref")
    Uni<GetMatchmakerMatchRuntimeRefResponse> execute(GetMatchmakerMatchRuntimeRefRequest request);

    @PUT
    @Path("/find-matchmaker-match-runtime-ref")
    Uni<FindMatchmakerMatchRuntimeRefResponse> execute(FindMatchmakerMatchRuntimeRefRequest request);

    @PUT
    @Path("/sync-matchmaker-match-runtime-ref")
    Uni<SyncMatchmakerMatchRuntimeRefResponse> execute(SyncMatchmakerMatchRuntimeRefRequest request);

    @PUT
    @Path("/delete-matchmaker-match-runtime-ref")
    Uni<DeleteMatchmakerMatchRuntimeRefResponse> execute(DeleteMatchmakerMatchRuntimeRefRequest request);

    /*
    MatchmakerState
     */

    @PUT
    @Path("/get-matchmaker-state")
    Uni<GetMatchmakerStateResponse> execute(GetMatchmakerStateRequest request);

    @PUT
    @Path("/update-matchmaker-state")
    Uni<UpdateMatchmakerStateResponse> execute(UpdateMatchmakerStateRequest request);
}
