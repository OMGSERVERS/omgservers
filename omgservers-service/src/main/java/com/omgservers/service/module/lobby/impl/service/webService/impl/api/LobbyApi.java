package com.omgservers.service.module.lobby.impl.service.webService.impl.api;

import com.omgservers.model.dto.lobby.DeleteLobbyRequest;
import com.omgservers.model.dto.lobby.DeleteLobbyResponse;
import com.omgservers.model.dto.lobby.DeleteLobbyRuntimeRequest;
import com.omgservers.model.dto.lobby.DeleteLobbyRuntimeResponse;
import com.omgservers.model.dto.lobby.FindLobbyRuntimeRequest;
import com.omgservers.model.dto.lobby.FindLobbyRuntimeResponse;
import com.omgservers.model.dto.lobby.GetLobbyRequest;
import com.omgservers.model.dto.lobby.GetLobbyResponse;
import com.omgservers.model.dto.lobby.GetLobbyRuntimeRequest;
import com.omgservers.model.dto.lobby.GetLobbyRuntimeResponse;
import com.omgservers.model.dto.lobby.SyncLobbyRequest;
import com.omgservers.model.dto.lobby.SyncLobbyResponse;
import com.omgservers.model.dto.lobby.SyncLobbyRuntimeRequest;
import com.omgservers.model.dto.lobby.SyncLobbyRuntimeResponse;
import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;

@Path("/omgservers/lobby-api/v1/request")
public interface LobbyApi {

    @PUT
    @Path("/get-lobby")
    Uni<GetLobbyResponse> getLobby(GetLobbyRequest request);

    @PUT
    @Path("/sync-lobby")
    Uni<SyncLobbyResponse> syncLobby(SyncLobbyRequest request);

    @PUT
    @Path("/delete-lobby")
    Uni<DeleteLobbyResponse> deleteLobby(DeleteLobbyRequest request);

    @PUT
    @Path("/get-lobby-runtime")
    Uni<GetLobbyRuntimeResponse> getLobbyRuntime(GetLobbyRuntimeRequest request);

    @PUT
    @Path("/find-lobby-runtime")
    Uni<FindLobbyRuntimeResponse> findLobbyRuntime(FindLobbyRuntimeRequest request);

    @PUT
    @Path("/sync-lobby-runtime")
    Uni<SyncLobbyRuntimeResponse> syncLobbyRuntime(SyncLobbyRuntimeRequest request);

    @PUT
    @Path("/delete-lobby-runtime")
    Uni<DeleteLobbyRuntimeResponse> deleteLobbyRuntime(DeleteLobbyRuntimeRequest request);
}
