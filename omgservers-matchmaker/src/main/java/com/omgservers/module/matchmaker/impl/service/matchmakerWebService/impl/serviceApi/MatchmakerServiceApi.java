package com.omgservers.module.matchmaker.impl.service.matchmakerWebService.impl.serviceApi;

import com.omgservers.dto.matchmaker.DeleteMatchShardResponse;
import com.omgservers.dto.matchmaker.DeleteMatchShardedRequest;
import com.omgservers.dto.matchmaker.DeleteMatchmakerShardResponse;
import com.omgservers.dto.matchmaker.DeleteMatchmakerShardedRequest;
import com.omgservers.dto.matchmaker.DeleteRequestShardResponse;
import com.omgservers.dto.matchmaker.DeleteRequestShardedRequest;
import com.omgservers.dto.matchmaker.DoMatchmakingShardResponse;
import com.omgservers.dto.matchmaker.DoMatchmakingShardedRequest;
import com.omgservers.dto.matchmaker.GetMatchShardResponse;
import com.omgservers.dto.matchmaker.GetMatchShardedRequest;
import com.omgservers.dto.matchmaker.GetMatchmakerShardResponse;
import com.omgservers.dto.matchmaker.GetMatchmakerShardedRequest;
import com.omgservers.dto.matchmaker.SyncMatchShardResponse;
import com.omgservers.dto.matchmaker.SyncMatchShardedRequest;
import com.omgservers.dto.matchmaker.SyncMatchmakerShardResponse;
import com.omgservers.dto.matchmaker.SyncMatchmakerShardedRequest;
import com.omgservers.dto.matchmaker.SyncRequestShardResponse;
import com.omgservers.dto.matchmaker.SyncRequestShardedRequest;
import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;

@Path("/omgservers/service-api/matchmaker-api/v1/request")
public interface MatchmakerServiceApi {

    @PUT
    @Path("/sync-matchmaker")
    Uni<SyncMatchmakerShardResponse> syncMatchmaker(SyncMatchmakerShardedRequest request);

    @PUT
    @Path("/get-matchmaker")
    Uni<GetMatchmakerShardResponse> getMatchmaker(GetMatchmakerShardedRequest request);

    @PUT
    @Path("/delete-matchmaker")
    Uni<DeleteMatchmakerShardResponse> deleteMatchmaker(DeleteMatchmakerShardedRequest request);

    @PUT
    @Path("/sync-request")
    Uni<SyncRequestShardResponse> syncRequest(SyncRequestShardedRequest request);

    @PUT
    @Path("/delete-request")
    Uni<DeleteRequestShardResponse> deleteRequest(DeleteRequestShardedRequest request);

    @PUT
    @Path("/get-match")
    Uni<GetMatchShardResponse> getMatch(GetMatchShardedRequest request);

    @PUT
    @Path("/sync-match")
    Uni<SyncMatchShardResponse> syncMatch(SyncMatchShardedRequest request);

    @PUT
    @Path("/delete-match")
    Uni<DeleteMatchShardResponse> deleteMatch(DeleteMatchShardedRequest request);

    @PUT
    @Path("/do-matchmaking")
    Uni<DoMatchmakingShardResponse> doMatchmaking(DoMatchmakingShardedRequest request);
}
