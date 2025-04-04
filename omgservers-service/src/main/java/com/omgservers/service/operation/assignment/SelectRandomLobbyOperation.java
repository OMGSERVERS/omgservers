package com.omgservers.service.operation.assignment;

import com.omgservers.schema.model.lobby.LobbyModel;
import io.smallrye.mutiny.Uni;

public interface SelectRandomLobbyOperation {
    Uni<LobbyModel> execute(Long tenantId, Long tenantDeploymentId);
}
