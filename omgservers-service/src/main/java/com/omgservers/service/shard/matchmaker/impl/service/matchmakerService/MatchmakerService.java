package com.omgservers.service.shard.matchmaker.impl.service.matchmakerService;

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
import jakarta.validation.Valid;

public interface MatchmakerService {

    /*
    Matchmaker
     */

    Uni<GetMatchmakerResponse> execute(@Valid GetMatchmakerRequest request);

    Uni<SyncMatchmakerResponse> execute(@Valid SyncMatchmakerRequest request);

    Uni<SyncMatchmakerResponse> executeWithIdempotency(@Valid SyncMatchmakerRequest request);

    Uni<DeleteMatchmakerResponse> execute(@Valid DeleteMatchmakerRequest request);

    /*
    MatchmakerAssignment
     */

    Uni<GetMatchmakerAssignmentResponse> execute(@Valid GetMatchmakerAssignmentRequest request);

    Uni<FindMatchmakerAssignmentResponse> execute(@Valid FindMatchmakerAssignmentRequest request);

    Uni<ViewMatchmakerAssignmentsResponse> execute(@Valid ViewMatchmakerAssignmentsRequest request);

    Uni<SyncMatchmakerAssignmentResponse> execute(@Valid SyncMatchmakerAssignmentRequest request);

    Uni<SyncMatchmakerAssignmentResponse> executeWithIdempotency(@Valid SyncMatchmakerAssignmentRequest request);

    Uni<DeleteMatchmakerAssignmentResponse> execute(@Valid DeleteMatchmakerAssignmentRequest request);

    /*
    MatchmakerCommand
     */

    Uni<ViewMatchmakerCommandsResponse> execute(@Valid ViewMatchmakerCommandsRequest request);

    Uni<SyncMatchmakerCommandResponse> execute(@Valid SyncMatchmakerCommandRequest request);

    Uni<SyncMatchmakerCommandResponse> executeWithIdempotency(@Valid SyncMatchmakerCommandRequest request);

    Uni<DeleteMatchmakerCommandResponse> execute(@Valid DeleteMatchmakerCommandRequest request);

    /*
    MatchmakerRequest
     */

    Uni<ViewMatchmakerRequestsResponse> execute(@Valid ViewMatchmakerRequestsRequest request);

    Uni<SyncMatchmakerRequestResponse> execute(@Valid SyncMatchmakerRequestRequest request);

    Uni<SyncMatchmakerRequestResponse> executeWithIdempotency(@Valid SyncMatchmakerRequestRequest request);

    Uni<DeleteMatchmakerRequestResponse> execute(@Valid DeleteMatchmakerRequestRequest request);

    /*
    MatchmakerMatch
     */

    Uni<GetMatchmakerMatchResponse> execute(@Valid GetMatchmakerMatchRequest request);

    Uni<ViewMatchmakerMatchesResponse> execute(@Valid ViewMatchmakerMatchesRequest request);

    Uni<SyncMatchmakerMatchResponse> execute(@Valid SyncMatchmakerMatchRequest request);

    Uni<UpdateMatchmakerMatchStatusResponse> execute(@Valid UpdateMatchmakerMatchStatusRequest request);

    Uni<DeleteMatchmakerMatchResponse> execute(@Valid DeleteMatchmakerMatchRequest request);

    /*
    MatchmakerMatchAssignment
     */

    Uni<GetMatchmakerMatchAssignmentResponse> execute(@Valid GetMatchmakerMatchAssignmentRequest request);

    Uni<FindMatchmakerMatchAssignmentResponse> execute(@Valid FindMatchmakerMatchAssignmentRequest request);

    Uni<ViewMatchmakerMatchAssignmentsResponse> execute(@Valid ViewMatchmakerMatchAssignmentsRequest request);

    Uni<SyncMatchmakerMatchAssignmentResponse> execute(@Valid SyncMatchmakerMatchAssignmentRequest request);

    Uni<DeleteMatchmakerMatchAssignmentResponse> execute(@Valid DeleteMatchmakerMatchAssignmentRequest request);

    /*
    MatchmakerMatchRuntimeRef
     */

    Uni<GetMatchmakerMatchRuntimeRefResponse> execute(@Valid GetMatchmakerMatchRuntimeRefRequest request);

    Uni<FindMatchmakerMatchRuntimeRefResponse> execute(@Valid FindMatchmakerMatchRuntimeRefRequest request);

    Uni<SyncMatchmakerMatchRuntimeRefResponse> execute(@Valid SyncMatchmakerMatchRuntimeRefRequest request);

    Uni<DeleteMatchmakerMatchRuntimeRefResponse> execute(@Valid DeleteMatchmakerMatchRuntimeRefRequest request);

    /*
    MatchmakerState
     */

    Uni<GetMatchmakerStateResponse> execute(@Valid GetMatchmakerStateRequest request);

    Uni<UpdateMatchmakerStateResponse> execute(@Valid UpdateMatchmakerStateRequest request);
}
