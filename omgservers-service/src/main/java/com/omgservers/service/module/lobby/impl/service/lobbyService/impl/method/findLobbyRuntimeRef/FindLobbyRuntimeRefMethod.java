package com.omgservers.service.module.lobby.impl.service.lobbyService.impl.method.findLobbyRuntimeRef;

import com.omgservers.model.dto.lobby.FindLobbyRuntimeRefRequest;
import com.omgservers.model.dto.lobby.FindLobbyRuntimeRefResponse;
import io.smallrye.mutiny.Uni;

public interface FindLobbyRuntimeRefMethod {
    Uni<FindLobbyRuntimeRefResponse> findLobbyRuntimeRef(FindLobbyRuntimeRefRequest request);
}
