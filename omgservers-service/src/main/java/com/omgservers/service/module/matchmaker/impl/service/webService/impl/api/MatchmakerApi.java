package com.omgservers.service.module.matchmaker.impl.service.webService.impl.api;

import com.omgservers.model.dto.matchmaker.DeleteMatchClientRequest;
import com.omgservers.model.dto.matchmaker.DeleteMatchClientResponse;
import com.omgservers.model.dto.matchmaker.DeleteMatchCommandRequest;
import com.omgservers.model.dto.matchmaker.DeleteMatchCommandResponse;
import com.omgservers.model.dto.matchmaker.DeleteMatchRequest;
import com.omgservers.model.dto.matchmaker.DeleteMatchResponse;
import com.omgservers.model.dto.matchmaker.DeleteMatchmakerCommandRequest;
import com.omgservers.model.dto.matchmaker.DeleteMatchmakerCommandResponse;
import com.omgservers.model.dto.matchmaker.DeleteMatchmakerRequest;
import com.omgservers.model.dto.matchmaker.DeleteMatchmakerResponse;
import com.omgservers.model.dto.matchmaker.DeleteRequestRequest;
import com.omgservers.model.dto.matchmaker.DeleteRequestResponse;
import com.omgservers.model.dto.matchmaker.FindMatchClientRequest;
import com.omgservers.model.dto.matchmaker.FindMatchClientResponse;
import com.omgservers.model.dto.matchmaker.GetMatchClientRequest;
import com.omgservers.model.dto.matchmaker.GetMatchClientResponse;
import com.omgservers.model.dto.matchmaker.GetMatchRequest;
import com.omgservers.model.dto.matchmaker.GetMatchResponse;
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
