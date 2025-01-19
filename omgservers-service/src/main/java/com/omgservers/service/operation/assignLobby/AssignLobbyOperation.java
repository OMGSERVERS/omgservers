package com.omgservers.service.operation.assignLobby;

import com.omgservers.schema.model.lobby.LobbyModel;
import io.smallrye.mutiny.Uni;

public interface AssignLobbyOperation {
    Uni<Boolean> execute(Long clientId, LobbyModel lobby, String idempotencyKey);
}
