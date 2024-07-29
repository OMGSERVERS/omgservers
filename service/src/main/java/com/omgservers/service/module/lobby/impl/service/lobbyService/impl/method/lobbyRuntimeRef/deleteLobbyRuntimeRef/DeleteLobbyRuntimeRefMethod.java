package com.omgservers.service.module.lobby.impl.service.lobbyService.impl.method.lobbyRuntimeRef.deleteLobbyRuntimeRef;

import com.omgservers.schema.module.lobby.DeleteLobbyRuntimeRefRequest;
import com.omgservers.schema.module.lobby.DeleteLobbyRuntimeRefResponse;
import io.smallrye.mutiny.Uni;

public interface DeleteLobbyRuntimeRefMethod {
    Uni<DeleteLobbyRuntimeRefResponse> deleteLobbyRuntimeRef(DeleteLobbyRuntimeRefRequest request);
}
