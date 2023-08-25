package com.omgservers.application.module.matchmakerModule.impl.service.matchmakerWebService.impl.serviceApi;

import com.omgservers.dto.matchmakerModule.DeleteMatchInternalRequest;
import com.omgservers.dto.matchmakerModule.DeleteMatchInternalResponse;
import com.omgservers.dto.matchmakerModule.DeleteMatchmakerInternalRequest;
import com.omgservers.dto.matchmakerModule.DeleteMatchmakerInternalResponse;
import com.omgservers.dto.matchmakerModule.DeleteRequestInternalRequest;
import com.omgservers.dto.matchmakerModule.DeleteRequestInternalResponse;
import com.omgservers.dto.matchmakerModule.DoMatchmakingInternalRequest;
import com.omgservers.dto.matchmakerModule.DoMatchmakingInternalResponse;
import com.omgservers.dto.matchmakerModule.GetMatchInternalRequest;
import com.omgservers.dto.matchmakerModule.GetMatchInternalResponse;
import com.omgservers.dto.matchmakerModule.GetMatchmakerInternalRequest;
import com.omgservers.dto.matchmakerModule.GetMatchmakerInternalResponse;
import com.omgservers.dto.matchmakerModule.SyncMatchInternalRequest;
import com.omgservers.dto.matchmakerModule.SyncMatchInternalResponse;
import com.omgservers.dto.matchmakerModule.SyncMatchmakerInternalRequest;
import com.omgservers.dto.matchmakerModule.SyncMatchmakerInternalResponse;
import com.omgservers.dto.matchmakerModule.SyncRequestInternalRequest;
import com.omgservers.dto.matchmakerModule.SyncRequestInternalResponse;
import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;

@Path("/omgservers/service-api/matchmaker-api/v1/request")
public interface MatchmakerServiceApi {

    @PUT
    @Path("/sync-matchmaker")
    Uni<SyncMatchmakerInternalResponse> syncMatchmaker(SyncMatchmakerInternalRequest request);

    @PUT
    @Path("/get-matchmaker")
    Uni<GetMatchmakerInternalResponse> getMatchmaker(GetMatchmakerInternalRequest request);

    @PUT
    @Path("/delete-matchmaker")
    Uni<DeleteMatchmakerInternalResponse> deleteMatchmaker(DeleteMatchmakerInternalRequest request);

    @PUT
    @Path("/sync-request")
    Uni<SyncRequestInternalResponse> syncRequest(SyncRequestInternalRequest request);

    @PUT
    @Path("/delete-request")
    Uni<DeleteRequestInternalResponse> deleteRequest(DeleteRequestInternalRequest request);

    @PUT
    @Path("/get-match")
    Uni<GetMatchInternalResponse> getMatch(GetMatchInternalRequest request);

    @PUT
    @Path("/sync-match")
    Uni<SyncMatchInternalResponse> syncMatch(SyncMatchInternalRequest request);

    @PUT
    @Path("/delete-match")
    Uni<DeleteMatchInternalResponse> deleteMatch(DeleteMatchInternalRequest request);

    @PUT
    @Path("/do-matchmaking")
    Uni<DoMatchmakingInternalResponse> doMatchmaking(DoMatchmakingInternalRequest request);
}
