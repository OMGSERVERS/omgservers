package com.omgservers.service.module.lobby.impl.service.lobbyService.impl.method.getLobbyRuntimeRef;

import com.omgservers.model.dto.lobby.GetLobbyRuntimeRefRequest;
import com.omgservers.model.dto.lobby.GetLobbyRuntimeRefResponse;
import io.smallrye.mutiny.Uni;

public interface GetLobbyRuntimeRefMethod {
    Uni<GetLobbyRuntimeRefResponse> getLobbyRuntimeRef(GetLobbyRuntimeRefRequest request);
}
