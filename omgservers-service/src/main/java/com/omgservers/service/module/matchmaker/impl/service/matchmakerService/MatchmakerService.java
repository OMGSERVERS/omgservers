package com.omgservers.service.module.matchmaker.impl.service.matchmakerService;

import com.omgservers.model.dto.matchmaker.DeleteMatchClientRequest;
import com.omgservers.model.dto.matchmaker.DeleteMatchClientResponse;
import com.omgservers.model.dto.matchmaker.DeleteMatchCommandRequest;
import com.omgservers.model.dto.matchmaker.DeleteMatchCommandResponse;
import com.omgservers.model.dto.matchmaker.DeleteMatchRequest;
import com.omgservers.model.dto.matchmaker.DeleteMatchResponse;
import com.omgservers.model.dto.matchmaker.DeleteMatchRuntimeRefRequest;
import com.omgservers.model.dto.matchmaker.DeleteMatchRuntimeRefResponse;
import com.omgservers.model.dto.matchmaker.DeleteMatchmakerCommandRequest;
import com.omgservers.model.dto.matchmaker.DeleteMatchmakerCommandResponse;
import com.omgservers.model.dto.matchmaker.DeleteMatchmakerRequest;
import com.omgservers.model.dto.matchmaker.DeleteMatchmakerResponse;
import com.omgservers.model.dto.matchmaker.DeleteRequestRequest;
import com.omgservers.model.dto.matchmaker.DeleteRequestResponse;
import com.omgservers.model.dto.matchmaker.FindMatchClientRequest;
import com.omgservers.model.dto.matchmaker.FindMatchClientResponse;
import com.omgservers.model.dto.matchmaker.FindMatchRuntimeRefRequest;
import com.omgservers.model.dto.matchmaker.FindMatchRuntimeRefResponse;
import com.omgservers.model.dto.matchmaker.GetMatchClientRequest;
import com.omgservers.model.dto.matchmaker.GetMatchClientResponse;
import com.omgservers.model.dto.matchmaker.GetMatchRequest;
import com.omgservers.model.dto.matchmaker.GetMatchResponse;
import com.omgservers.model.dto.matchmaker.GetMatchRuntimeRefRequest;
import com.omgservers.model.dto.matchmaker.GetMatchRuntimeRefResponse;
import com.omgservers.model.dto.matchmaker.GetMatchmakerRequest;
import com.omgservers.model.dto.matchmaker.GetMatchmakerResponse;
import com.omgservers.model.dto.matchmaker.GetMatchmakerStateRequest;
import com.omgservers.model.dto.matchmaker.GetMatchmakerStateResponse;
import com.omgservers.model.dto.matchmaker.SyncMatchClientRequest;
import com.omgservers.model.dto.matchmaker.SyncMatchClientResponse;
import com.omgservers.model.dto.matchmaker.SyncMatchCommandRequest;
import com.omgservers.model.dto.matchmaker.SyncMatchCommandResponse;
import com.omgservers.model.dto.matchmaker.SyncMatchRequest;
import com.omgservers.model.dto.matchmaker.SyncMatchResponse;
import com.omgservers.model.dto.matchmaker.SyncMatchRuntimeRefRequest;
import com.omgservers.model.dto.matchmaker.SyncMatchRuntimeRefResponse;
import com.omgservers.model.dto.matchmaker.SyncMatchmakerCommandRequest;
import com.omgservers.model.dto.matchmaker.SyncMatchmakerCommandResponse;
import com.omgservers.model.dto.matchmaker.SyncMatchmakerRequest;
import com.omgservers.model.dto.matchmaker.SyncMatchmakerResponse;
import com.omgservers.model.dto.matchmaker.SyncRequestRequest;
import com.omgservers.model.dto.matchmaker.SyncRequestResponse;
import com.omgservers.model.dto.matchmaker.UpdateMatchmakerStateRequest;
import com.omgservers.model.dto.matchmaker.UpdateMatchmakerStateResponse;
import com.omgservers.model.dto.matchmaker.ViewMatchClientsRequest;
import com.omgservers.model.dto.matchmaker.ViewMatchClientsResponse;
import com.omgservers.model.dto.matchmaker.ViewMatchCommandsRequest;
import com.omgservers.model.dto.matchmaker.ViewMatchCommandsResponse;
import com.omgservers.model.dto.matchmaker.ViewMatchesRequest;
import com.omgservers.model.dto.matchmaker.ViewMatchesResponse;
import com.omgservers.model.dto.matchmaker.ViewMatchmakerCommandsRequest;
import com.omgservers.model.dto.matchmaker.ViewMatchmakerCommandsResponse;
import com.omgservers.model.dto.matchmaker.ViewRequestsRequest;
import com.omgservers.model.dto.matchmaker.ViewRequestsResponse;
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

    Uni<SyncRequestResponse> syncRequest(@Valid SyncRequestRequest request);

    Uni<DeleteRequestResponse> deleteRequest(@Valid DeleteRequestRequest request);

    Uni<ViewRequestsResponse> viewRequests(@Valid ViewRequestsRequest request);

    Uni<GetMatchResponse> getMatch(@Valid GetMatchRequest request);

    Uni<ViewMatchesResponse> viewMatches(@Valid ViewMatchesRequest request);

    Uni<SyncMatchResponse> syncMatch(@Valid SyncMatchRequest request);

    Uni<DeleteMatchResponse> deleteMatch(@Valid DeleteMatchRequest request);

    Uni<ViewMatchCommandsResponse> viewMatchCommands(@Valid ViewMatchCommandsRequest request);

    Uni<SyncMatchCommandResponse> syncMatchCommand(@Valid SyncMatchCommandRequest request);

    Uni<DeleteMatchCommandResponse> deleteMatchCommand(@Valid DeleteMatchCommandRequest request);

    Uni<GetMatchClientResponse> getMatchClient(@Valid GetMatchClientRequest request);

    Uni<FindMatchClientResponse> findMatchClient(@Valid FindMatchClientRequest request);

    Uni<ViewMatchClientsResponse> viewMatchClients(@Valid ViewMatchClientsRequest request);

    Uni<SyncMatchClientResponse> syncMatchClient(@Valid SyncMatchClientRequest request);

    Uni<DeleteMatchClientResponse> deleteMatchClient(@Valid DeleteMatchClientRequest request);

    Uni<GetMatchRuntimeRefResponse> getMatchRuntimeRef(@Valid GetMatchRuntimeRefRequest request);

    Uni<FindMatchRuntimeRefResponse> findMatchRuntimeRef(@Valid FindMatchRuntimeRefRequest request);

    Uni<SyncMatchRuntimeRefResponse> syncMatchRuntimeRef(@Valid SyncMatchRuntimeRefRequest request);

    Uni<DeleteMatchRuntimeRefResponse> deleteMatchRuntimeRef(@Valid DeleteMatchRuntimeRefRequest request);
}
