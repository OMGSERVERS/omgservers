package com.omgservers.service.module.lobby.impl.service.lobbyService.impl.method.getLobby;

import com.omgservers.model.dto.lobby.GetLobbyRequest;
import com.omgservers.model.dto.lobby.GetLobbyResponse;
import io.smallrye.mutiny.Uni;

public interface GetLobbyMethod {
    Uni<GetLobbyResponse> getLobby(GetLobbyRequest request);
}
