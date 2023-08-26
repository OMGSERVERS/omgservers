package com.omgservers.application.module.matchmakerModule.impl.service.matchmakerWebService.impl.serviceApi;

import com.omgservers.dto.matchmakerModule.DeleteMatchShardRequest;
import com.omgservers.dto.matchmakerModule.DeleteMatchInternalResponse;
import com.omgservers.dto.matchmakerModule.DeleteMatchmakerShardRequest;
import com.omgservers.dto.matchmakerModule.DeleteMatchmakerInternalResponse;
import com.omgservers.dto.matchmakerModule.DeleteRequestShardRequest;
import com.omgservers.dto.matchmakerModule.DeleteRequestInternalResponse;
import com.omgservers.dto.matchmakerModule.DoMatchmakingShardRequest;
import com.omgservers.dto.matchmakerModule.DoMatchmakingInternalResponse;
import com.omgservers.dto.matchmakerModule.GetMatchShardRequest;
import com.omgservers.dto.matchmakerModule.GetMatchInternalResponse;
import com.omgservers.dto.matchmakerModule.GetMatchmakerShardRequest;
import com.omgservers.dto.matchmakerModule.GetMatchmakerInternalResponse;
import com.omgservers.dto.matchmakerModule.SyncMatchShardRequest;
import com.omgservers.dto.matchmakerModule.SyncMatchInternalResponse;
import com.omgservers.dto.matchmakerModule.SyncMatchmakerShardRequest;
import com.omgservers.dto.matchmakerModule.SyncMatchmakerInternalResponse;
import com.omgservers.dto.matchmakerModule.SyncRequestShardRequest;
import com.omgservers.dto.matchmakerModule.SyncRequestInternalResponse;
import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;

@Path("/omgservers/service-api/matchmaker-api/v1/request")
public interface MatchmakerServiceApi {

    @PUT
    @Path("/sync-matchmaker")
    Uni<SyncMatchmakerInternalResponse> syncMatchmaker(SyncMatchmakerShardRequest request);

    @PUT
    @Path("/get-matchmaker")
    Uni<GetMatchmakerInternalResponse> getMatchmaker(GetMatchmakerShardRequest request);

    @PUT
    @Path("/delete-matchmaker")
    Uni<DeleteMatchmakerInternalResponse> deleteMatchmaker(DeleteMatchmakerShardRequest request);

    @PUT
    @Path("/sync-request")
    Uni<SyncRequestInternalResponse> syncRequest(SyncRequestShardRequest request);

    @PUT
    @Path("/delete-request")
    Uni<DeleteRequestInternalResponse> deleteRequest(DeleteRequestShardRequest request);

    @PUT
    @Path("/get-match")
    Uni<GetMatchInternalResponse> getMatch(GetMatchShardRequest request);

    @PUT
    @Path("/sync-match")
    Uni<SyncMatchInternalResponse> syncMatch(SyncMatchShardRequest request);

    @PUT
    @Path("/delete-match")
    Uni<DeleteMatchInternalResponse> deleteMatch(DeleteMatchShardRequest request);

    @PUT
    @Path("/do-matchmaking")
    Uni<DoMatchmakingInternalResponse> doMatchmaking(DoMatchmakingShardRequest request);
}
