package com.omgservers.application.module.matchmakerModule.impl.service.matchmakerWebService.impl.serviceApi;

import com.omgservers.dto.matchmakerModule.DeleteMatchRoutedRequest;
import com.omgservers.dto.matchmakerModule.DeleteMatchInternalResponse;
import com.omgservers.dto.matchmakerModule.DeleteMatchmakerRoutedRequest;
import com.omgservers.dto.matchmakerModule.DeleteMatchmakerInternalResponse;
import com.omgservers.dto.matchmakerModule.DeleteRequestRoutedRequest;
import com.omgservers.dto.matchmakerModule.DeleteRequestInternalResponse;
import com.omgservers.dto.matchmakerModule.DoMatchmakingRoutedRequest;
import com.omgservers.dto.matchmakerModule.DoMatchmakingInternalResponse;
import com.omgservers.dto.matchmakerModule.GetMatchRoutedRequest;
import com.omgservers.dto.matchmakerModule.GetMatchInternalResponse;
import com.omgservers.dto.matchmakerModule.GetMatchmakerRoutedRequest;
import com.omgservers.dto.matchmakerModule.GetMatchmakerInternalResponse;
import com.omgservers.dto.matchmakerModule.SyncMatchRoutedRequest;
import com.omgservers.dto.matchmakerModule.SyncMatchInternalResponse;
import com.omgservers.dto.matchmakerModule.SyncMatchmakerRoutedRequest;
import com.omgservers.dto.matchmakerModule.SyncMatchmakerInternalResponse;
import com.omgservers.dto.matchmakerModule.SyncRequestRoutedRequest;
import com.omgservers.dto.matchmakerModule.SyncRequestInternalResponse;
import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;

@Path("/omgservers/service-api/matchmaker-api/v1/request")
public interface MatchmakerServiceApi {

    @PUT
    @Path("/sync-matchmaker")
    Uni<SyncMatchmakerInternalResponse> syncMatchmaker(SyncMatchmakerRoutedRequest request);

    @PUT
    @Path("/get-matchmaker")
    Uni<GetMatchmakerInternalResponse> getMatchmaker(GetMatchmakerRoutedRequest request);

    @PUT
    @Path("/delete-matchmaker")
    Uni<DeleteMatchmakerInternalResponse> deleteMatchmaker(DeleteMatchmakerRoutedRequest request);

    @PUT
    @Path("/sync-request")
    Uni<SyncRequestInternalResponse> syncRequest(SyncRequestRoutedRequest request);

    @PUT
    @Path("/delete-request")
    Uni<DeleteRequestInternalResponse> deleteRequest(DeleteRequestRoutedRequest request);

    @PUT
    @Path("/get-match")
    Uni<GetMatchInternalResponse> getMatch(GetMatchRoutedRequest request);

    @PUT
    @Path("/sync-match")
    Uni<SyncMatchInternalResponse> syncMatch(SyncMatchRoutedRequest request);

    @PUT
    @Path("/delete-match")
    Uni<DeleteMatchInternalResponse> deleteMatch(DeleteMatchRoutedRequest request);

    @PUT
    @Path("/do-matchmaking")
    Uni<DoMatchmakingInternalResponse> doMatchmaking(DoMatchmakingRoutedRequest request);
}
