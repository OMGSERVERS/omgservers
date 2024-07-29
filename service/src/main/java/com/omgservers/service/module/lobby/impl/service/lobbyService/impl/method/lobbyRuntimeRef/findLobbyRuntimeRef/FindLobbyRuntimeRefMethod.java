package com.omgservers.service.module.lobby.impl.service.lobbyService.impl.method.lobbyRuntimeRef.findLobbyRuntimeRef;

import com.omgservers.schema.module.lobby.FindLobbyRuntimeRefRequest;
import com.omgservers.schema.module.lobby.FindLobbyRuntimeRefResponse;
import io.smallrye.mutiny.Uni;

public interface FindLobbyRuntimeRefMethod {
    Uni<FindLobbyRuntimeRefResponse> findLobbyRuntimeRef(FindLobbyRuntimeRefRequest request);
}
