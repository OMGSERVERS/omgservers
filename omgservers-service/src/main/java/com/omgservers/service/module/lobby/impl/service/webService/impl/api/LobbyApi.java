package com.omgservers.service.module.lobby.impl.service.webService.impl.api;

import com.omgservers.model.dto.lobby.DeleteLobbyRequest;
import com.omgservers.model.dto.lobby.DeleteLobbyResponse;
import com.omgservers.model.dto.lobby.DeleteLobbyRuntimeRefRequest;
import com.omgservers.model.dto.lobby.DeleteLobbyRuntimeRefResponse;
import com.omgservers.model.dto.lobby.FindLobbyRuntimeRefRequest;
import com.omgservers.model.dto.lobby.FindLobbyRuntimeRefResponse;
import com.omgservers.model.dto.lobby.GetLobbyRequest;
import com.omgservers.model.dto.lobby.GetLobbyResponse;
import com.omgservers.model.dto.lobby.GetLobbyRuntimeRefRequest;
import com.omgservers.model.dto.lobby.GetLobbyRuntimeRefResponse;
import com.omgservers.model.dto.lobby.SyncLobbyRequest;
import com.omgservers.model.dto.lobby.SyncLobbyResponse;
import com.omgservers.model.dto.lobby.SyncLobbyRuntimeRefRequest;
import com.omgservers.model.dto.lobby.SyncLobbyRuntimeResponse;
import com.omgservers.service.configuration.ServiceOpenApiConfiguration;
import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import org.eclipse.microprofile.openapi.annotations.security.SecurityRequirement;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

@Tag(name = "Lobby Module API")
@Path("/omgservers/v1/module/lobby/request")
@SecurityRequirement(name = ServiceOpenApiConfiguration.SERVICE_SECURITY_SCHEMA)
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
    @Path("/get-lobby-runtime-ref")
    Uni<GetLobbyRuntimeRefResponse> getLobbyRuntimeRef(GetLobbyRuntimeRefRequest request);

    @PUT
    @Path("/find-lobby-runtime-ref")
    Uni<FindLobbyRuntimeRefResponse> findLobbyRuntimeRef(FindLobbyRuntimeRefRequest request);

    @PUT
    @Path("/sync-lobby-runtime-ref")
    Uni<SyncLobbyRuntimeResponse> syncLobbyRuntimeRef(SyncLobbyRuntimeRefRequest request);

    @PUT
    @Path("/delete-lobby-runtime-ref")
    Uni<DeleteLobbyRuntimeRefResponse> deleteLobbyRuntimeRef(DeleteLobbyRuntimeRefRequest request);
}
