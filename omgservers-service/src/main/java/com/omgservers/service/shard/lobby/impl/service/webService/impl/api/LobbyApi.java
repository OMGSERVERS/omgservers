package com.omgservers.service.shard.lobby.impl.service.webService.impl.api;

import com.omgservers.schema.module.lobby.DeleteLobbyRequest;
import com.omgservers.schema.module.lobby.DeleteLobbyResponse;
import com.omgservers.schema.module.lobby.DeleteLobbyRuntimeRefRequest;
import com.omgservers.schema.module.lobby.DeleteLobbyRuntimeRefResponse;
import com.omgservers.schema.module.lobby.FindLobbyRuntimeRefRequest;
import com.omgservers.schema.module.lobby.FindLobbyRuntimeRefResponse;
import com.omgservers.schema.module.lobby.GetLobbyRequest;
import com.omgservers.schema.module.lobby.GetLobbyResponse;
import com.omgservers.schema.module.lobby.GetLobbyRuntimeRefRequest;
import com.omgservers.schema.module.lobby.GetLobbyRuntimeRefResponse;
import com.omgservers.schema.module.lobby.SyncLobbyRequest;
import com.omgservers.schema.module.lobby.SyncLobbyResponse;
import com.omgservers.schema.module.lobby.SyncLobbyRuntimeRefRequest;
import com.omgservers.schema.module.lobby.SyncLobbyRuntimeRefResponse;
import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

@Tag(name = "Lobby Shard API")
@Path("/service/v1/shard/lobby/request")
public interface LobbyApi {

    @POST
    @Path("/get-lobby")
    Uni<GetLobbyResponse> getLobby(GetLobbyRequest request);

    @POST
    @Path("/sync-lobby")
    Uni<SyncLobbyResponse> syncLobby(SyncLobbyRequest request);

    @POST
    @Path("/delete-lobby")
    Uni<DeleteLobbyResponse> deleteLobby(DeleteLobbyRequest request);

    @POST
    @Path("/get-lobby-runtime-ref")
    Uni<GetLobbyRuntimeRefResponse> getLobbyRuntimeRef(GetLobbyRuntimeRefRequest request);

    @POST
    @Path("/find-lobby-runtime-ref")
    Uni<FindLobbyRuntimeRefResponse> findLobbyRuntimeRef(FindLobbyRuntimeRefRequest request);

    @POST
    @Path("/sync-lobby-runtime-ref")
    Uni<SyncLobbyRuntimeRefResponse> syncLobbyRuntimeRef(SyncLobbyRuntimeRefRequest request);

    @POST
    @Path("/delete-lobby-runtime-ref")
    Uni<DeleteLobbyRuntimeRefResponse> deleteLobbyRuntimeRef(DeleteLobbyRuntimeRefRequest request);
}
