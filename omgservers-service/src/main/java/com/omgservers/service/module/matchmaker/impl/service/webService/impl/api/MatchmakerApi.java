package com.omgservers.service.module.matchmaker.impl.service.webService.impl.api;

import com.omgservers.model.dto.matchmaker.DeleteMatchCommandRequest;
import com.omgservers.model.dto.matchmaker.DeleteMatchCommandResponse;
import com.omgservers.model.dto.matchmaker.DeleteMatchmakerCommandRequest;
import com.omgservers.model.dto.matchmaker.DeleteMatchmakerCommandResponse;
import com.omgservers.model.dto.matchmaker.DeleteMatchmakerMatchClientRequest;
import com.omgservers.model.dto.matchmaker.DeleteMatchmakerMatchClientResponse;
import com.omgservers.model.dto.matchmaker.DeleteMatchmakerMatchRequest;
import com.omgservers.model.dto.matchmaker.DeleteMatchmakerMatchResponse;
import com.omgservers.model.dto.matchmaker.DeleteMatchmakerMatchRuntimeRefRequest;
import com.omgservers.model.dto.matchmaker.DeleteMatchmakerMatchRuntimeRefResponse;
import com.omgservers.model.dto.matchmaker.DeleteMatchmakerRequest;
import com.omgservers.model.dto.matchmaker.DeleteMatchmakerRequestRequest;
import com.omgservers.model.dto.matchmaker.DeleteMatchmakerRequestResponse;
import com.omgservers.model.dto.matchmaker.DeleteMatchmakerResponse;
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
import com.omgservers.model.dto.matchmaker.SyncMatchCommandRequest;
import com.omgservers.model.dto.matchmaker.SyncMatchCommandResponse;
import com.omgservers.model.dto.matchmaker.SyncMatchmakerCommandRequest;
import com.omgservers.model.dto.matchmaker.SyncMatchmakerCommandResponse;
import com.omgservers.model.dto.matchmaker.SyncMatchmakerMatchClientRequest;
import com.omgservers.model.dto.matchmaker.SyncMatchmakerMatchClientResponse;
import com.omgservers.model.dto.matchmaker.SyncMatchmakerMatchRequest;
import com.omgservers.model.dto.matchmaker.SyncMatchmakerMatchResponse;
import com.omgservers.model.dto.matchmaker.SyncMatchmakerMatchRuntimeRefRequest;
import com.omgservers.model.dto.matchmaker.SyncMatchmakerMatchRuntimeRefResponse;
import com.omgservers.model.dto.matchmaker.SyncMatchmakerRequest;
import com.omgservers.model.dto.matchmaker.SyncMatchmakerRequestRequest;
import com.omgservers.model.dto.matchmaker.SyncMatchmakerRequestResponse;
import com.omgservers.model.dto.matchmaker.SyncMatchmakerResponse;
import com.omgservers.model.dto.matchmaker.UpdateMatchmakerStateRequest;
import com.omgservers.model.dto.matchmaker.UpdateMatchmakerStateResponse;
import com.omgservers.model.dto.matchmaker.ViewMatchmakerCommandsRequest;
import com.omgservers.model.dto.matchmaker.ViewMatchmakerCommandsResponse;
import com.omgservers.model.dto.matchmaker.ViewMatchmakerMatchClientsRequest;
import com.omgservers.model.dto.matchmaker.ViewMatchmakerMatchClientsResponse;
import com.omgservers.model.dto.matchmaker.ViewMatchmakerMatchCommandsRequest;
import com.omgservers.model.dto.matchmaker.ViewMatchmakerMatchCommandsResponse;
import com.omgservers.model.dto.matchmaker.ViewMatchmakerMatchesRequest;
import com.omgservers.model.dto.matchmaker.ViewMatchmakerMatchesResponse;
import com.omgservers.model.dto.matchmaker.ViewMatchmakerRequestsRequest;
import com.omgservers.model.dto.matchmaker.ViewMatchmakerRequestsResponse;
import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;

@Path("/omgservers/matchmaker-api/v1/request")
public interface MatchmakerApi {

    @PUT
    @Path("/get-matchmaker")
    Uni<GetMatchmakerResponse> getMatchmaker(GetMatchmakerRequest request);

    @PUT
    @Path("/sync-matchmaker")
    Uni<SyncMatchmakerResponse> syncMatchmaker(SyncMatchmakerRequest request);

    @PUT
    @Path("/delete-matchmaker")
    Uni<DeleteMatchmakerResponse> deleteMatchmaker(DeleteMatchmakerRequest request);

    @PUT
    @Path("/get-matchmaker-state")
    Uni<GetMatchmakerStateResponse> getMatchmakerState(GetMatchmakerStateRequest request);

    @PUT
    @Path("/update-matchmaker-state")
    Uni<UpdateMatchmakerStateResponse> updateMatchmakerState(UpdateMatchmakerStateRequest request);

    @PUT
    @Path("/sync-matchmaker-command")
    Uni<SyncMatchmakerCommandResponse> syncMatchmakerCommand(SyncMatchmakerCommandRequest request);

    @PUT
    @Path("/view-matchmaker-commands")
    Uni<ViewMatchmakerCommandsResponse> viewMatchmakerCommands(ViewMatchmakerCommandsRequest request);

    @PUT
    @Path("/delete-matchmaker-command")
    Uni<DeleteMatchmakerCommandResponse> deleteMatchmakerCommand(DeleteMatchmakerCommandRequest request);

    @PUT
    @Path("/sync-matchmaker-request")
    Uni<SyncMatchmakerRequestResponse> syncMatchmakerRequest(SyncMatchmakerRequestRequest request);

    @PUT
    @Path("/view-matchmaker-requests")
    Uni<ViewMatchmakerRequestsResponse> viewMatchmakerRequests(ViewMatchmakerRequestsRequest request);

    @PUT
    @Path("/delete-matchmaker-request")
    Uni<DeleteMatchmakerRequestResponse> deleteMatchmakerRequest(DeleteMatchmakerRequestRequest request);

    @PUT
    @Path("/get-matchmaker-match")
    Uni<GetMatchmakerMatchResponse> getMatchmakerMatch(GetMatchmakerMatchRequest request);

    @PUT
    @Path("/view-matchmaker-matches")
    Uni<ViewMatchmakerMatchesResponse> viewMatchmakerMatches(ViewMatchmakerMatchesRequest request);

    @PUT
    @Path("/sync-matchmaker-match")
    Uni<SyncMatchmakerMatchResponse> syncMatchmakerMatch(SyncMatchmakerMatchRequest request);

    @PUT
    @Path("/delete-matchmaker-match")
    Uni<DeleteMatchmakerMatchResponse> deleteMatchmakerMatch(DeleteMatchmakerMatchRequest request);

    @PUT
    @Path("/view-matchmaker-match-commands")
    Uni<ViewMatchmakerMatchCommandsResponse> viewMatchCommands(ViewMatchmakerMatchCommandsRequest request);

    @PUT
    @Path("/sync-matchmaker-match-command")
    Uni<SyncMatchCommandResponse> syncMatchmakerMatchCommand(SyncMatchCommandRequest request);

    @PUT
    @Path("/delete-matchmaker-match-command")
    Uni<DeleteMatchCommandResponse> deleteMatchCommand(DeleteMatchCommandRequest request);

    @PUT
    @Path("/get-matchmaker-match-client")
    Uni<GetMatchmakerMatchClientResponse> getMatchmakerMatchClient(GetMatchmakerMatchClientRequest request);

    @PUT
    @Path("/find-matchmaker-match-client")
    Uni<FindMatchmakerMatchClientResponse> findMatchmakerMatchClient(FindMatchmakerMatchClientRequest request);

    @PUT
    @Path("/view-matchmaker-match-clients")
    Uni<ViewMatchmakerMatchClientsResponse> viewMatchmakerMatchClients(ViewMatchmakerMatchClientsRequest request);

    @PUT
    @Path("/sync-matchmaker-match-client")
    Uni<SyncMatchmakerMatchClientResponse> syncMatchmakerMatchClient(SyncMatchmakerMatchClientRequest request);

    @PUT
    @Path("/delete-matchmaker-match-client")
    Uni<DeleteMatchmakerMatchClientResponse> deleteMatchmakerMatchClient(DeleteMatchmakerMatchClientRequest request);

    @PUT
    @Path("/get-matchmaker-match-runtime-ref")
    Uni<GetMatchmakerMatchRuntimeRefResponse> getMatchmakerMatchRuntimeRef(GetMatchmakerMatchRuntimeRefRequest request);

    @PUT
    @Path("/find-matchmaker-match-runtime-ref")
    Uni<FindMatchmakerMatchRuntimeRefResponse> findMatchmakerMatchRuntimeRef(
            FindMatchmakerMatchRuntimeRefRequest request);

    @PUT
    @Path("/sync-matchmaker-match-runtime-ref")
    Uni<SyncMatchmakerMatchRuntimeRefResponse> syncMatchmakerMatchRuntimeRef(
            SyncMatchmakerMatchRuntimeRefRequest request);

    @PUT
    @Path("/delete-matchmaker-match-runtime-ref")
    Uni<DeleteMatchmakerMatchRuntimeRefResponse> deleteMatchmakerMatchRuntimeRef(
            DeleteMatchmakerMatchRuntimeRefRequest request);
}
