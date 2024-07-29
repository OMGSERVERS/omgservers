package com.omgservers.service.module.lobby.impl.service.lobbyService.impl.method.lobby.deleteLobby;

import com.omgservers.schema.module.lobby.DeleteLobbyRequest;
import com.omgservers.schema.module.lobby.DeleteLobbyResponse;
import io.smallrye.mutiny.Uni;

public interface DeleteLobbyMethod {
    Uni<DeleteLobbyResponse> deleteLobby(DeleteLobbyRequest request);
}
