package com.omgservers.module.matchmaker.impl.service.matchmakerWebService.impl.serviceApi;

import com.omgservers.dto.matchmaker.DeleteMatchShardedResponse;
import com.omgservers.dto.matchmaker.DeleteMatchShardedRequest;
import com.omgservers.dto.matchmaker.DeleteMatchmakerShardedResponse;
import com.omgservers.dto.matchmaker.DeleteMatchmakerShardedRequest;
import com.omgservers.dto.matchmaker.DeleteRequestShardedResponse;
import com.omgservers.dto.matchmaker.DeleteRequestShardedRequest;
import com.omgservers.dto.matchmaker.DoMatchmakingShardedResponse;
import com.omgservers.dto.matchmaker.DoMatchmakingShardedRequest;
import com.omgservers.dto.matchmaker.GetMatchShardedResponse;
import com.omgservers.dto.matchmaker.GetMatchShardedRequest;
import com.omgservers.dto.matchmaker.GetMatchmakerShardedResponse;
import com.omgservers.dto.matchmaker.GetMatchmakerShardedRequest;
import com.omgservers.dto.matchmaker.SyncMatchShardedResponse;
import com.omgservers.dto.matchmaker.SyncMatchShardedRequest;
import com.omgservers.dto.matchmaker.SyncMatchmakerShardedResponse;
import com.omgservers.dto.matchmaker.SyncMatchmakerShardedRequest;
import com.omgservers.dto.matchmaker.SyncRequestShardedResponse;
import com.omgservers.dto.matchmaker.SyncRequestShardedRequest;
import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;

@Path("/omgservers/service-api/matchmaker-api/v1/request")
public interface MatchmakerServiceApi {

    @PUT
    @Path("/sync-matchmaker")
    Uni<SyncMatchmakerShardedResponse> syncMatchmaker(SyncMatchmakerShardedRequest request);

    @PUT
    @Path("/get-matchmaker")
    Uni<GetMatchmakerShardedResponse> getMatchmaker(GetMatchmakerShardedRequest request);

    @PUT
    @Path("/delete-matchmaker")
    Uni<DeleteMatchmakerShardedResponse> deleteMatchmaker(DeleteMatchmakerShardedRequest request);

    @PUT
    @Path("/sync-request")
    Uni<SyncRequestShardedResponse> syncRequest(SyncRequestShardedRequest request);

    @PUT
    @Path("/delete-request")
    Uni<DeleteRequestShardedResponse> deleteRequest(DeleteRequestShardedRequest request);

    @PUT
    @Path("/get-match")
    Uni<GetMatchShardedResponse> getMatch(GetMatchShardedRequest request);

    @PUT
    @Path("/sync-match")
    Uni<SyncMatchShardedResponse> syncMatch(SyncMatchShardedRequest request);

    @PUT
    @Path("/delete-match")
    Uni<DeleteMatchShardedResponse> deleteMatch(DeleteMatchShardedRequest request);

    @PUT
    @Path("/do-matchmaking")
    Uni<DoMatchmakingShardedResponse> doMatchmaking(DoMatchmakingShardedRequest request);
}