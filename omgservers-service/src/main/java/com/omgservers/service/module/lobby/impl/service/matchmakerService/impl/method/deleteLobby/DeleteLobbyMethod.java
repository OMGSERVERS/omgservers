package com.omgservers.service.module.lobby.impl.service.matchmakerService.impl.method.deleteLobby;

import com.omgservers.model.dto.lobby.DeleteLobbyRequest;
import com.omgservers.model.dto.lobby.DeleteLobbyResponse;
import io.smallrye.mutiny.Uni;

public interface DeleteLobbyMethod {
    Uni<DeleteLobbyResponse> deleteLobby(DeleteLobbyRequest request);
}
