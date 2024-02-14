package com.omgservers.service.module.lobby.impl.service.matchmakerService.impl.method.getLobbyRuntime;

import com.omgservers.model.dto.lobby.GetLobbyRuntimeRequest;
import com.omgservers.model.dto.lobby.GetLobbyRuntimeResponse;
import io.smallrye.mutiny.Uni;

public interface GetLobbyRuntimeMethod {
    Uni<GetLobbyRuntimeResponse> getLobbyRuntime(GetLobbyRuntimeRequest request);
}
