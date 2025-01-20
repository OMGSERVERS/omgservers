package com.omgservers.service.shard.matchmaker.impl.service.webService;

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

public interface WebService {

    /*
    Matchmaker
     */

    Uni<SyncMatchmakerResponse> execute(SyncMatchmakerRequest request);

    Uni<GetMatchmakerResponse> execute(GetMatchmakerRequest request);

    Uni<DeleteMatchmakerResponse> execute(DeleteMatchmakerRequest request);

    /*
    MatchmakerAssignment
     */

    Uni<GetMatchmakerAssignmentResponse> execute(GetMatchmakerAssignmentRequest request);

    Uni<FindMatchmakerAssignmentResponse> execute(FindMatchmakerAssignmentRequest request);

    Uni<ViewMatchmakerAssignmentsResponse> execute(ViewMatchmakerAssignmentsRequest request);

    Uni<SyncMatchmakerAssignmentResponse> execute(SyncMatchmakerAssignmentRequest request);

    Uni<DeleteMatchmakerAssignmentResponse> execute(DeleteMatchmakerAssignmentRequest request);

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
    MatchmakerMatch
     */

    Uni<GetMatchmakerMatchResponse> execute(GetMatchmakerMatchRequest request);

    Uni<ViewMatchmakerMatchesResponse> execute(ViewMatchmakerMatchesRequest request);

    Uni<SyncMatchmakerMatchResponse> execute(SyncMatchmakerMatchRequest request);

    Uni<UpdateMatchmakerMatchStatusResponse> execute(UpdateMatchmakerMatchStatusRequest request);

    Uni<DeleteMatchmakerMatchResponse> execute(DeleteMatchmakerMatchRequest request);

    /*
    MatchmakerMatchAssignment
     */

    Uni<GetMatchmakerMatchAssignmentResponse> execute(GetMatchmakerMatchAssignmentRequest request);

    Uni<FindMatchmakerMatchAssignmentResponse> execute(FindMatchmakerMatchAssignmentRequest request);

    Uni<ViewMatchmakerMatchAssignmentsResponse> execute(ViewMatchmakerMatchAssignmentsRequest request);

    Uni<SyncMatchmakerMatchAssignmentResponse> execute(SyncMatchmakerMatchAssignmentRequest request);

    Uni<DeleteMatchmakerMatchAssignmentResponse> execute(DeleteMatchmakerMatchAssignmentRequest request);

    /*
    MatchmakerMatchRuntimeRef
     */

    Uni<GetMatchmakerMatchRuntimeRefResponse> execute(GetMatchmakerMatchRuntimeRefRequest request);

    Uni<FindMatchmakerMatchRuntimeRefResponse> execute(FindMatchmakerMatchRuntimeRefRequest request);

    Uni<SyncMatchmakerMatchRuntimeRefResponse> execute(SyncMatchmakerMatchRuntimeRefRequest request);

    Uni<DeleteMatchmakerMatchRuntimeRefResponse> execute(DeleteMatchmakerMatchRuntimeRefRequest request);

    /*
    MatchmakerState
     */

    Uni<GetMatchmakerStateResponse> execute(GetMatchmakerStateRequest request);

    Uni<UpdateMatchmakerStateResponse> execute(UpdateMatchmakerStateRequest request);
}
