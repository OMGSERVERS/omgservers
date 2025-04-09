package com.omgservers.service.operation.deployment;

import io.smallrye.mutiny.Uni;

public interface CreateOpenLobbyDeploymentCommandOperation {
    Uni<Boolean> execute(Long deploymentId,
                         Long lobbyId);

    Uni<Boolean> executeFailSafe(Long deploymentId,
                                 Long lobbyId);
}
