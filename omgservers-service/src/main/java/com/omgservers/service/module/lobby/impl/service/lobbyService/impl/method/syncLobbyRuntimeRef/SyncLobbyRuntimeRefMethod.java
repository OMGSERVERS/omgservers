package com.omgservers.service.module.lobby.impl.service.lobbyService.impl.method.syncLobbyRuntimeRef;

import com.omgservers.model.dto.lobby.SyncLobbyRuntimeRefRequest;
import com.omgservers.model.dto.lobby.SyncLobbyRuntimeResponse;
import io.smallrye.mutiny.Uni;

public interface SyncLobbyRuntimeRefMethod {
    Uni<SyncLobbyRuntimeResponse> syncLobbyRuntimeRef(SyncLobbyRuntimeRefRequest request);
}
