package com.omgservers.service.shard.lobby.impl.service.lobbyService.impl.method;

import com.omgservers.schema.module.lobby.SyncLobbyRequest;
import com.omgservers.schema.module.lobby.SyncLobbyResponse;
import io.smallrye.mutiny.Uni;

public interface SyncLobbyMethod {

    Uni<SyncLobbyResponse> execute(SyncLobbyRequest request);
}
