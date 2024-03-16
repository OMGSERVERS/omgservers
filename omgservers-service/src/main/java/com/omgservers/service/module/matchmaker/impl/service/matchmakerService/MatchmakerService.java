package com.omgservers.service.module.matchmaker.impl.service.matchmakerService;

import com.omgservers.model.dto.matchmaker.DeleteMatchmakerMatchClientRequest;
import com.omgservers.model.dto.matchmaker.DeleteMatchmakerMatchClientResponse;
import com.omgservers.model.dto.matchmaker.DeleteMatchCommandRequest;
import com.omgservers.model.dto.matchmaker.DeleteMatchCommandResponse;
import com.omgservers.model.dto.matchmaker.DeleteMatchmakerMatchRequest;
import com.omgservers.model.dto.matchmaker.DeleteMatchmakerMatchResponse;
import com.omgservers.model.dto.matchmaker.DeleteMatchmakerMatchRuntimeRefRequest;
import com.omgservers.model.dto.matchmaker.DeleteMatchmakerMatchRuntimeRefResponse;
import com.omgservers.model.dto.matchmaker.DeleteMatchmakerCommandRequest;
import com.omgservers.model.dto.matchmaker.DeleteMatchmakerCommandResponse;
import com.omgservers.model.dto.matchmaker.DeleteMatchmakerRequest;
import com.omgservers.model.dto.matchmaker.DeleteMatchmakerResponse;
import com.omgservers.model.dto.matchmaker.DeleteMatchmakerRequestRequest;
import com.omgservers.model.dto.matchmaker.DeleteMatchmakerRequestResponse;
import com.omgservers.model.dto.matchmaker.FindMatchmakerMatchClientRequest;
import com.omgservers.model.dto.matchmaker.FindMatchmakerMatchClientResponse;
import com.omgservers.model.dto.matchmaker.FindMatchmakerMatchRuntimeRefRequest;
import com.omgservers.model.dto.matchmaker.FindMatchmakerMatchRuntimeRefResponse;
import com.omgservers.model.dto.matchmaker.GetMatchmakerMatchClientRequest;
import com.omgservers.model.dto.matchmaker.GetMatchmakerMatchClientResponse;
import com.omgservers.model.dto.matchmaker.GetMatchmakerMatchRequest;
import com.omgservers.model.dto.matchmaker.GetMatchmakerMatchResponse;
import com.omgservers.model.dto.matchmaker.GetMatchmakerMatchRuntimeRefRequest;
import com.omgservers.model.dto.matchmaker.GetMatchmakerMatchRuntimeRefResponse;
import com.omgservers.model.dto.matchmaker.GetMatchmakerRequest;
import com.omgservers.model.dto.matchmaker.GetMatchmakerResponse;
import com.omgservers.model.dto.matchmaker.GetMatchmakerStateRequest;
import com.omgservers.model.dto.matchmaker.GetMatchmakerStateResponse;
import com.omgservers.model.dto.matchmaker.SyncMatchmakerMatchClientRequest;
import com.omgservers.model.dto.matchmaker.SyncMatchmakerMatchClientResponse;
import com.omgservers.model.dto.matchmaker.SyncMatchCommandRequest;
import com.omgservers.model.dto.matchmaker.SyncMatchCommandResponse;
import com.omgservers.model.dto.matchmaker.SyncMatchmakerMatchRequest;
import com.omgservers.model.dto.matchmaker.SyncMatchmakerMatchResponse;
import com.omgservers.model.dto.matchmaker.SyncMatchmakerMatchRuntimeRefRequest;
import com.omgservers.model.dto.matchmaker.SyncMatchmakerMatchRuntimeRefResponse;
import com.omgservers.model.dto.matchmaker.SyncMatchmakerCommandRequest;
import com.omgservers.model.dto.matchmaker.SyncMatchmakerCommandResponse;
import com.omgservers.model.dto.matchmaker.SyncMatchmakerRequest;
import com.omgservers.model.dto.matchmaker.SyncMatchmakerResponse;
import com.omgservers.model.dto.matchmaker.SyncMatchmakerRequestRequest;
import com.omgservers.model.dto.matchmaker.SyncMatchmakerRequestResponse;
import com.omgservers.model.dto.matchmaker.UpdateMatchmakerStateRequest;
import com.omgservers.model.dto.matchmaker.UpdateMatchmakerStateResponse;
import com.omgservers.model.dto.matchmaker.ViewMatchmakerMatchClientsRequest;
import com.omgservers.model.dto.matchmaker.ViewMatchmakerMatchClientsResponse;
import com.omgservers.model.dto.matchmaker.ViewMatchmakerMatchCommandsRequest;
import com.omgservers.model.dto.matchmaker.ViewMatchmakerMatchCommandsResponse;
import com.omgservers.model.dto.matchmaker.ViewMatchmakerMatchesRequest;
import com.omgservers.model.dto.matchmaker.ViewMatchmakerMatchesResponse;
import com.omgservers.model.dto.matchmaker.ViewMatchmakerCommandsRequest;
import com.omgservers.model.dto.matchmaker.ViewMatchmakerCommandsResponse;
import com.omgservers.model.dto.matchmaker.ViewMatchmakerRequestsRequest;
import com.omgservers.model.dto.matchmaker.ViewMatchmakerRequestsResponse;
import io.smallrye.mutiny.Uni;
import jakarta.validation.Valid;

public interface MatchmakerService {
    Uni<SyncMatchmakerResponse> syncMatchmaker(@Valid SyncMatchmakerRequest request);

    Uni<GetMatchmakerResponse> getMatchmaker(@Valid GetMatchmakerRequest request);

    Uni<DeleteMatchmakerResponse> deleteMatchmaker(@Valid DeleteMatchmakerRequest request);

    Uni<GetMatchmakerStateResponse> getMatchmakerState(@Valid GetMatchmakerStateRequest request);

    Uni<UpdateMatchmakerStateResponse> updateMatchmakerState(@Valid UpdateMatchmakerStateRequest request);

    Uni<SyncMatchmakerCommandResponse> syncMatchmakerCommand(@Valid SyncMatchmakerCommandRequest request);

    Uni<DeleteMatchmakerCommandResponse> deleteMatchmakerCommand(@Valid DeleteMatchmakerCommandRequest request);

    Uni<ViewMatchmakerCommandsResponse> viewMatchmakerCommands(@Valid ViewMatchmakerCommandsRequest request);

    Uni<SyncMatchmakerRequestResponse> syncMatchmakerRequest(@Valid SyncMatchmakerRequestRequest request);

    Uni<DeleteMatchmakerRequestResponse> deleteMatchmakerRequest(@Valid DeleteMatchmakerRequestRequest request);

    Uni<ViewMatchmakerRequestsResponse> viewMatchmakerRequests(@Valid ViewMatchmakerRequestsRequest request);

    Uni<GetMatchmakerMatchResponse> getMatchmakerMatch(@Valid GetMatchmakerMatchRequest request);

    Uni<ViewMatchmakerMatchesResponse> viewMatchmakerMatches(@Valid ViewMatchmakerMatchesRequest request);

    Uni<SyncMatchmakerMatchResponse> syncMatchmakerMatch(@Valid SyncMatchmakerMatchRequest request);

    Uni<DeleteMatchmakerMatchResponse> deleteMatchmakerMatch(@Valid DeleteMatchmakerMatchRequest request);

    Uni<ViewMatchmakerMatchCommandsResponse> viewMatchmakerMatchCommands(@Valid ViewMatchmakerMatchCommandsRequest request);

    Uni<SyncMatchCommandResponse> syncMatchmakerMatchCommand(@Valid SyncMatchCommandRequest request);

    Uni<DeleteMatchCommandResponse> deleteMatchmakerMatchCommand(@Valid DeleteMatchCommandRequest request);

    Uni<GetMatchmakerMatchClientResponse> getMatchmakerMatchClient(@Valid GetMatchmakerMatchClientRequest request);

    Uni<FindMatchmakerMatchClientResponse> findMatchmakerMatchClient(@Valid FindMatchmakerMatchClientRequest request);

    Uni<ViewMatchmakerMatchClientsResponse> viewMatchmakerMatchClients(@Valid ViewMatchmakerMatchClientsRequest request);

    Uni<SyncMatchmakerMatchClientResponse> syncMatchmakerMatchClient(@Valid SyncMatchmakerMatchClientRequest request);

    Uni<DeleteMatchmakerMatchClientResponse> deleteMatchmakerMatchClient(@Valid DeleteMatchmakerMatchClientRequest request);

    Uni<GetMatchmakerMatchRuntimeRefResponse> getMatchmakerMatchRuntimeRef(@Valid GetMatchmakerMatchRuntimeRefRequest request);

    Uni<FindMatchmakerMatchRuntimeRefResponse> findMatchmakerMatchRuntimeRef(@Valid FindMatchmakerMatchRuntimeRefRequest request);

    Uni<SyncMatchmakerMatchRuntimeRefResponse> syncMatchmakerMatchRuntimeRef(@Valid SyncMatchmakerMatchRuntimeRefRequest request);

    Uni<DeleteMatchmakerMatchRuntimeRefResponse> deleteMatchmakerMatchRuntimeRef(@Valid DeleteMatchmakerMatchRuntimeRefRequest request);
}
