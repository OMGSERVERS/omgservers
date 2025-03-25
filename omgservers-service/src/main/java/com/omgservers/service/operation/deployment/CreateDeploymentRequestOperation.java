package com.omgservers.service.operation.deployment;

import io.smallrye.mutiny.Uni;

public interface CreateDeploymentRequestOperation {
    Uni<Boolean> execute(Long deploymentId,
                         Long clientId,
                         String idempotencyKey);
}
