package com.omgservers.service.module.lobby.impl.service.lobbyService.impl.method.syncLobby;

import com.omgservers.model.dto.lobby.SyncLobbyRequest;
import com.omgservers.model.dto.lobby.SyncLobbyResponse;
import io.smallrye.mutiny.Uni;

public interface SyncLobbyMethod {

    Uni<SyncLobbyResponse> syncLobby(SyncLobbyRequest request);
}
