package com.omgservers.service.module.lobby.impl.service.lobbyService.impl.method.deleteLobbyRuntimeRef;

import com.omgservers.model.dto.lobby.DeleteLobbyRuntimeRefRequest;
import com.omgservers.model.dto.lobby.DeleteLobbyRuntimeRefResponse;
import io.smallrye.mutiny.Uni;

public interface DeleteLobbyRuntimeRefMethod {
    Uni<DeleteLobbyRuntimeRefResponse> deleteLobbyRuntimeRef(DeleteLobbyRuntimeRefRequest request);
}
