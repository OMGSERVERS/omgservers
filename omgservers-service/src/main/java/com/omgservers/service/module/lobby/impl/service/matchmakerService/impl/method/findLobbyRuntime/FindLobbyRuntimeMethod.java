package com.omgservers.service.module.lobby.impl.service.matchmakerService.impl.method.findLobbyRuntime;

import com.omgservers.model.dto.lobby.FindLobbyRuntimeRequest;
import com.omgservers.model.dto.lobby.FindLobbyRuntimeResponse;
import io.smallrye.mutiny.Uni;

public interface FindLobbyRuntimeMethod {
    Uni<FindLobbyRuntimeResponse> findLobbyRuntime(FindLobbyRuntimeRequest request);
}
