package com.omgservers.service.operation.deployment;

import io.smallrye.mutiny.Uni;

public interface CreateDeploymentMatchmakerResourceOperation {
    Uni<Boolean> execute(Long deploymentId,
                         String idempotencyKey);
}
