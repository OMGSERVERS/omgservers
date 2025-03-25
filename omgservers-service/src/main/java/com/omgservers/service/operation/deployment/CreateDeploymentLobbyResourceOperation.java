package com.omgservers.service.operation.deployment;

import io.smallrye.mutiny.Uni;

public interface CreateDeploymentLobbyResourceOperation {
    Uni<Boolean> execute(Long deploymentId,
                         String idempotencyKey);
}
