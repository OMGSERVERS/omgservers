package com.omgservers.service.shard.lobby.impl.service.lobbyService.impl.method.lobby.deleteLobby;

import com.omgservers.schema.module.lobby.DeleteLobbyRequest;
import com.omgservers.schema.module.lobby.DeleteLobbyResponse;
import io.smallrye.mutiny.Uni;

public interface DeleteLobbyMethod {
    Uni<DeleteLobbyResponse> deleteLobby(DeleteLobbyRequest request);
}
