package com.omgservers.module.matchmaker.impl.service.webService.impl.api;

import com.omgservers.dto.matchmaker.DeleteMatchClientRequest;
import com.omgservers.dto.matchmaker.DeleteMatchClientResponse;
import com.omgservers.dto.matchmaker.DeleteMatchCommandRequest;
import com.omgservers.dto.matchmaker.DeleteMatchCommandResponse;
import com.omgservers.dto.matchmaker.DeleteMatchRequest;
import com.omgservers.dto.matchmaker.DeleteMatchResponse;
import com.omgservers.dto.matchmaker.DeleteMatchmakerCommandRequest;
import com.omgservers.dto.matchmaker.DeleteMatchmakerCommandResponse;
import com.omgservers.dto.matchmaker.DeleteMatchmakerRequest;
import com.omgservers.dto.matchmaker.DeleteMatchmakerResponse;
import com.omgservers.dto.matchmaker.DeleteRequestRequest;
import com.omgservers.dto.matchmaker.DeleteRequestResponse;
import com.omgservers.dto.matchmaker.FindMatchClientRequest;
import com.omgservers.dto.matchmaker.FindMatchClientResponse;
import com.omgservers.dto.matchmaker.GetMatchClientRequest;
import com.omgservers.dto.matchmaker.GetMatchClientResponse;
import com.omgservers.dto.matchmaker.GetMatchRequest;
import com.omgservers.dto.matchmaker.GetMatchResponse;
import com.omgservers.dto.matchmaker.GetMatchmakerRequest;
import com.omgservers.dto.matchmaker.GetMatchmakerResponse;
import com.omgservers.dto.matchmaker.GetMatchmakerStateRequest;
import com.omgservers.dto.matchmaker.GetMatchmakerStateResponse;
import com.omgservers.dto.matchmaker.SyncMatchClientRequest;
import com.omgservers.dto.matchmaker.SyncMatchClientResponse;
import com.omgservers.dto.matchmaker.SyncMatchCommandRequest;
import com.omgservers.dto.matchmaker.SyncMatchCommandResponse;
import com.omgservers.dto.matchmaker.SyncMatchRequest;
import com.omgservers.dto.matchmaker.SyncMatchResponse;
import com.omgservers.dto.matchmaker.SyncMatchmakerCommandRequest;
import com.omgservers.dto.matchmaker.SyncMatchmakerCommandResponse;
import com.omgservers.dto.matchmaker.SyncMatchmakerRequest;
import com.omgservers.dto.matchmaker.SyncMatchmakerResponse;
import com.omgservers.dto.matchmaker.SyncRequestRequest;
import com.omgservers.dto.matchmaker.SyncRequestResponse;
import com.omgservers.dto.matchmaker.UpdateMatchmakerStateRequest;
import com.omgservers.dto.matchmaker.UpdateMatchmakerStateResponse;
import com.omgservers.dto.matchmaker.ViewMatchClientsRequest;
import com.omgservers.dto.matchmaker.ViewMatchClientsResponse;
import com.omgservers.dto.matchmaker.ViewMatchCommandsRequest;
import com.omgservers.dto.matchmaker.ViewMatchCommandsResponse;
import com.omgservers.dto.matchmaker.ViewMatchesRequest;
import com.omgservers.dto.matchmaker.ViewMatchesResponse;
import com.omgservers.dto.matchmaker.ViewMatchmakerCommandsRequest;
import com.omgservers.dto.matchmaker.ViewMatchmakerCommandsResponse;
import com.omgservers.dto.matchmaker.ViewRequestsRequest;
import com.omgservers.dto.matchmaker.ViewRequestsResponse;
import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;

@Path("/omgservers/matchmaker-api/v1/request")
public interface MatchmakerApi {

    @PUT
    @Path("/sync-matchmaker")
    Uni<SyncMatchmakerResponse> syncMatchmaker(SyncMatchmakerRequest request);

    @PUT
    @Path("/get-matchmaker")
    Uni<GetMatchmakerResponse> getMatchmaker(GetMatchmakerRequest request);

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
    @Path("/delete-matchmaker-command")
    Uni<DeleteMatchmakerCommandResponse> deleteMatchmakerCommand(DeleteMatchmakerCommandRequest request);

    @PUT
    @Path("/view-matchmaker-commands")
    Uni<ViewMatchmakerCommandsResponse> viewMatchmakerCommands(ViewMatchmakerCommandsRequest request);

    @PUT
    @Path("/sync-request")
    Uni<SyncRequestResponse> syncRequest(SyncRequestRequest request);

    @PUT
    @Path("/delete-request")
    Uni<DeleteRequestResponse> deleteRequest(DeleteRequestRequest request);

    @PUT
    @Path("/view-requests")
    Uni<ViewRequestsResponse> viewRequests(ViewRequestsRequest request);

    @PUT
    @Path("/get-match")
    Uni<GetMatchResponse> getMatch(GetMatchRequest request);

    @PUT
    @Path("/sync-match")
    Uni<SyncMatchResponse> syncMatch(SyncMatchRequest request);

    @PUT
    @Path("/delete-match")
    Uni<DeleteMatchResponse> deleteMatch(DeleteMatchRequest request);

    @PUT
    @Path("/view-matches")
    Uni<ViewMatchesResponse> viewMatches(ViewMatchesRequest request);

    @PUT
    @Path("/sync-match-command")
    Uni<SyncMatchCommandResponse> syncMatchCommand(SyncMatchCommandRequest request);

    @PUT
    @Path("/delete-match-command")
    Uni<DeleteMatchCommandResponse> deleteMatchCommand(DeleteMatchCommandRequest request);

    @PUT
    @Path("/view-match-commands")
    Uni<ViewMatchCommandsResponse> viewMatchCommands(ViewMatchCommandsRequest request);

    @PUT
    @Path("/get-match-client")
    Uni<GetMatchClientResponse> getMatchClient(GetMatchClientRequest request);

    @PUT
    @Path("/sync-match-client")
    Uni<SyncMatchClientResponse> syncMatchClient(SyncMatchClientRequest request);

    @PUT
    @Path("/delete-match-client")
    Uni<DeleteMatchClientResponse> deleteMatchClient(DeleteMatchClientRequest request);

    @PUT
    @Path("/find-match-client")
    Uni<FindMatchClientResponse> findMatchClient(FindMatchClientRequest request);

    @PUT
    @Path("/view-match-clients")
    Uni<ViewMatchClientsResponse> viewMatchClients(ViewMatchClientsRequest request);
}
