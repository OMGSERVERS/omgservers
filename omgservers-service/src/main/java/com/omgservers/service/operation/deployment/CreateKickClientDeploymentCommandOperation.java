package com.omgservers.service.operation.deployment;

import io.smallrye.mutiny.Uni;

public interface CreateKickClientDeploymentCommandOperation {
    Uni<Boolean> execute(Long clientId,
                         String idempotencyKey);
}
