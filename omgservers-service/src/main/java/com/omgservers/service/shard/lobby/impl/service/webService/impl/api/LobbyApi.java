package com.omgservers.service.shard.lobby.impl.service.webService.impl.api;

import com.omgservers.schema.shard.lobby.DeleteLobbyRequest;
import com.omgservers.schema.shard.lobby.DeleteLobbyResponse;
import com.omgservers.schema.shard.lobby.GetLobbyRequest;
import com.omgservers.schema.shard.lobby.GetLobbyResponse;
import com.omgservers.schema.shard.lobby.SyncLobbyRequest;
import com.omgservers.schema.shard.lobby.SyncLobbyResponse;
import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

@Tag(name = "Lobby Shard API")
@Path("/service/v1/shard/lobby/request")
public interface LobbyApi {

    @POST
    @Path("/get-lobby")
    Uni<GetLobbyResponse> execute(GetLobbyRequest request);

    @POST
    @Path("/sync-lobby")
    Uni<SyncLobbyResponse> execute(SyncLobbyRequest request);

    @POST
    @Path("/delete-lobby")
    Uni<DeleteLobbyResponse> execute(DeleteLobbyRequest request);
}
