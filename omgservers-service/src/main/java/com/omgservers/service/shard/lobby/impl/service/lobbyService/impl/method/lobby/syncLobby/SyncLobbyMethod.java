package com.omgservers.service.shard.lobby.impl.service.lobbyService.impl.method.lobby.syncLobby;

import com.omgservers.schema.module.lobby.SyncLobbyRequest;
import com.omgservers.schema.module.lobby.SyncLobbyResponse;
import io.smallrye.mutiny.Uni;

public interface SyncLobbyMethod {

    Uni<SyncLobbyResponse> syncLobby(SyncLobbyRequest request);
}
