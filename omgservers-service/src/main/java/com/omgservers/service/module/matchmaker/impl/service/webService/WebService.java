package com.omgservers.service.module.matchmaker.impl.service.webService;

import com.omgservers.schema.module.matchmaker.DeleteMatchmakerAssignmentRequest;
import com.omgservers.schema.module.matchmaker.DeleteMatchmakerAssignmentResponse;
import com.omgservers.schema.module.matchmaker.DeleteMatchmakerCommandRequest;
import com.omgservers.schema.module.matchmaker.DeleteMatchmakerCommandResponse;
import com.omgservers.schema.module.matchmaker.DeleteMatchmakerMatchClientRequest;
import com.omgservers.schema.module.matchmaker.DeleteMatchmakerMatchClientResponse;
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
import com.omgservers.schema.module.matchmaker.FindMatchmakerMatchClientRequest;
import com.omgservers.schema.module.matchmaker.FindMatchmakerMatchClientResponse;
import com.omgservers.schema.module.matchmaker.FindMatchmakerMatchRuntimeRefRequest;
import com.omgservers.schema.module.matchmaker.FindMatchmakerMatchRuntimeRefResponse;
import com.omgservers.schema.module.matchmaker.GetMatchmakerAssignmentRequest;
import com.omgservers.schema.module.matchmaker.GetMatchmakerAssignmentResponse;
import com.omgservers.schema.module.matchmaker.GetMatchmakerMatchClientRequest;
import com.omgservers.schema.module.matchmaker.GetMatchmakerMatchClientResponse;
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
import com.omgservers.schema.module.matchmaker.SyncMatchmakerMatchClientRequest;
import com.omgservers.schema.module.matchmaker.SyncMatchmakerMatchClientResponse;
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
import com.omgservers.schema.module.matchmaker.ViewMatchmakerMatchClientsRequest;
import com.omgservers.schema.module.matchmaker.ViewMatchmakerMatchClientsResponse;
import com.omgservers.schema.module.matchmaker.ViewMatchmakerMatchesRequest;
import com.omgservers.schema.module.matchmaker.ViewMatchmakerMatchesResponse;
import com.omgservers.schema.module.matchmaker.ViewMatchmakerRequestsRequest;
import com.omgservers.schema.module.matchmaker.ViewMatchmakerRequestsResponse;
import io.smallrye.mutiny.Uni;

public interface WebService {

    Uni<SyncMatchmakerResponse> syncMatchmaker(SyncMatchmakerRequest request);

    Uni<GetMatchmakerResponse> getMatchmaker(GetMatchmakerRequest request);

    Uni<DeleteMatchmakerResponse> deleteMatchmaker(DeleteMatchmakerRequest request);

    Uni<GetMatchmakerAssignmentResponse> getMatchmakerAssignment(GetMatchmakerAssignmentRequest request);

    Uni<FindMatchmakerAssignmentResponse> findMatchmakerAssignment(FindMatchmakerAssignmentRequest request);

    Uni<ViewMatchmakerAssignmentsResponse> viewMatchmakerAssignments(ViewMatchmakerAssignmentsRequest request);

    Uni<SyncMatchmakerAssignmentResponse> syncMatchmakerAssignment(SyncMatchmakerAssignmentRequest request);

    Uni<DeleteMatchmakerAssignmentResponse> deleteMatchmakerAssignment(DeleteMatchmakerAssignmentRequest request);

    Uni<GetMatchmakerStateResponse> getMatchmakerState(GetMatchmakerStateRequest request);

    Uni<UpdateMatchmakerStateResponse> updateMatchmakerState(UpdateMatchmakerStateRequest request);

    Uni<SyncMatchmakerCommandResponse> syncMatchmakerCommand(SyncMatchmakerCommandRequest request);

    Uni<DeleteMatchmakerCommandResponse> deleteMatchmakerCommand(DeleteMatchmakerCommandRequest request);

    Uni<ViewMatchmakerCommandsResponse> viewMatchmakerCommands(ViewMatchmakerCommandsRequest request);

    Uni<SyncMatchmakerRequestResponse> syncMatchmakerRequest(SyncMatchmakerRequestRequest request);

    Uni<DeleteMatchmakerRequestResponse> deleteMatchmakerRequest(DeleteMatchmakerRequestRequest request);

    Uni<ViewMatchmakerRequestsResponse> viewMatchmakerRequests(ViewMatchmakerRequestsRequest request);

    Uni<GetMatchmakerMatchResponse> getMatchmakerMatch(GetMatchmakerMatchRequest request);

    Uni<UpdateMatchmakerMatchStatusResponse> updateMatchmakerMatchStatus(UpdateMatchmakerMatchStatusRequest request);

    Uni<SyncMatchmakerMatchResponse> syncMatchmakerMatch(SyncMatchmakerMatchRequest request);

    Uni<DeleteMatchmakerMatchResponse> deleteMatchmakerMatch(DeleteMatchmakerMatchRequest request);

    Uni<ViewMatchmakerMatchesResponse> viewMatchmakerMatches(ViewMatchmakerMatchesRequest request);

    Uni<GetMatchmakerMatchClientResponse> getMatchmakerMatchClient(GetMatchmakerMatchClientRequest request);

    Uni<FindMatchmakerMatchClientResponse> findMatchmakerMatchClient(FindMatchmakerMatchClientRequest request);

    Uni<ViewMatchmakerMatchClientsResponse> viewMatchmakerMatchClients(ViewMatchmakerMatchClientsRequest request);

    Uni<SyncMatchmakerMatchClientResponse> syncMatchmakerMatchClient(SyncMatchmakerMatchClientRequest request);

    Uni<DeleteMatchmakerMatchClientResponse> deleteMatchmakerMatchClient(DeleteMatchmakerMatchClientRequest request);

    Uni<GetMatchmakerMatchRuntimeRefResponse> getMatchmakerMatchRuntimeRef(GetMatchmakerMatchRuntimeRefRequest request);

    Uni<FindMatchmakerMatchRuntimeRefResponse> findMatchmakerMatchRuntimeRef(
            FindMatchmakerMatchRuntimeRefRequest request);

    Uni<SyncMatchmakerMatchRuntimeRefResponse> syncMatchmakerMatchRuntimeRef(
            SyncMatchmakerMatchRuntimeRefRequest request);

    Uni<DeleteMatchmakerMatchRuntimeRefResponse> deleteMatchmakerMatchRuntimeRef(
            DeleteMatchmakerMatchRuntimeRefRequest request);
}
