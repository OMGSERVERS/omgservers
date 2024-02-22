package com.omgservers.service.module.matchmaker.impl.service.webService;

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

public interface WebService {

    Uni<SyncMatchmakerResponse> syncMatchmaker(SyncMatchmakerRequest request);

    Uni<GetMatchmakerResponse> getMatchmaker(GetMatchmakerRequest request);

    Uni<DeleteMatchmakerResponse> deleteMatchmaker(DeleteMatchmakerRequest request);

    Uni<GetMatchmakerStateResponse> getMatchmakerState(GetMatchmakerStateRequest request);

    Uni<UpdateMatchmakerStateResponse> updateMatchmakerState(UpdateMatchmakerStateRequest request);

    Uni<SyncMatchmakerCommandResponse> syncMatchmakerCommand(SyncMatchmakerCommandRequest request);

    Uni<DeleteMatchmakerCommandResponse> deleteMatchmakerCommand(DeleteMatchmakerCommandRequest request);

    Uni<ViewMatchmakerCommandsResponse> viewMatchmakerCommands(ViewMatchmakerCommandsRequest request);

    Uni<SyncRequestResponse> syncRequest(SyncRequestRequest request);

    Uni<DeleteRequestResponse> deleteRequest(DeleteRequestRequest request);

    Uni<ViewRequestsResponse> viewRequests(ViewRequestsRequest request);

    Uni<GetMatchResponse> getMatch(GetMatchRequest request);

    Uni<SyncMatchResponse> syncMatch(SyncMatchRequest request);

    Uni<DeleteMatchResponse> deleteMatch(DeleteMatchRequest request);

    Uni<ViewMatchesResponse> viewMatches(ViewMatchesRequest request);

    Uni<ViewMatchCommandsResponse> viewMatchCommands(ViewMatchCommandsRequest request);

    Uni<SyncMatchCommandResponse> syncMatchCommand(SyncMatchCommandRequest request);

    Uni<DeleteMatchCommandResponse> deleteMatchCommand(DeleteMatchCommandRequest request);

    Uni<GetMatchClientResponse> getMatchClient(GetMatchClientRequest request);

    Uni<FindMatchClientResponse> findMatchClient(FindMatchClientRequest request);

    Uni<ViewMatchClientsResponse> viewMatchClients(ViewMatchClientsRequest request);

    Uni<SyncMatchClientResponse> syncMatchClient(SyncMatchClientRequest request);

    Uni<DeleteMatchClientResponse> deleteMatchClient(DeleteMatchClientRequest request);

    Uni<GetMatchRuntimeRefResponse> getMatchRuntimeRef(GetMatchRuntimeRefRequest request);

    Uni<FindMatchRuntimeRefResponse> findMatchRuntimeRef(FindMatchRuntimeRefRequest request);

    Uni<SyncMatchRuntimeRefResponse> syncMatchRuntimeRef(SyncMatchRuntimeRefRequest request);

    Uni<DeleteMatchRuntimeRefResponse> deleteMatchRuntimeRef(DeleteMatchRuntimeRefRequest request);
}
