package com.omgservers.service.module.lobby.impl.service.matchmakerService.impl.method.deleteLobbyRuntime;

import com.omgservers.model.dto.lobby.DeleteLobbyRuntimeRequest;
import com.omgservers.model.dto.lobby.DeleteLobbyRuntimeResponse;
import io.smallrye.mutiny.Uni;

public interface DeleteLobbyRuntimeMethod {
    Uni<DeleteLobbyRuntimeResponse> deleteLobbyRuntime(DeleteLobbyRuntimeRequest request);
}
