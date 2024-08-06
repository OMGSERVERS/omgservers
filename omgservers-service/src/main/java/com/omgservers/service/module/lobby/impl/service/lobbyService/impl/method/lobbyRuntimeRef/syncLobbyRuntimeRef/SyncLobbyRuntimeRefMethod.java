package com.omgservers.service.module.lobby.impl.service.lobbyService.impl.method.lobbyRuntimeRef.syncLobbyRuntimeRef;

import com.omgservers.schema.module.lobby.SyncLobbyRuntimeRefRequest;
import com.omgservers.schema.module.lobby.SyncLobbyRuntimeRefResponse;
import io.smallrye.mutiny.Uni;

public interface SyncLobbyRuntimeRefMethod {
    Uni<SyncLobbyRuntimeRefResponse> syncLobbyRuntimeRef(SyncLobbyRuntimeRefRequest request);
}
