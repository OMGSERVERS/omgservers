package com.omgservers.service.shard.lobby.impl.service.lobbyService.impl.method;

import com.omgservers.schema.module.lobby.DeleteLobbyRequest;
import com.omgservers.schema.module.lobby.DeleteLobbyResponse;
import io.smallrye.mutiny.Uni;

public interface DeleteLobbyMethod {
    Uni<DeleteLobbyResponse> execute(DeleteLobbyRequest request);
}
