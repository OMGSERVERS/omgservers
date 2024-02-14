package com.omgservers.service.module.lobby.impl.service.matchmakerService.impl.method.syncLobbyRuntime;

import com.omgservers.model.dto.lobby.SyncLobbyRuntimeRequest;
import com.omgservers.model.dto.lobby.SyncLobbyRuntimeResponse;
import io.smallrye.mutiny.Uni;

public interface SyncLobbyRuntimeMethod {
    Uni<SyncLobbyRuntimeResponse> syncLobbyRuntime(SyncLobbyRuntimeRequest request);
}
