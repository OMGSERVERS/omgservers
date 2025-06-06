package com.omgservers.service.operation.deployment;

import io.smallrye.mutiny.Uni;

public interface CreateOpenMatchmakerDeploymentCommandOperation {
    Uni<Boolean> execute(Long deploymentId,
                         Long matchmakerId);

    Uni<Boolean> executeFailSafe(Long deploymentId,
                                 Long matchmakerId);
}
