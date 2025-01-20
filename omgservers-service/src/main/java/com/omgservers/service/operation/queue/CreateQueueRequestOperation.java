package com.omgservers.service.operation.queue;

import io.smallrye.mutiny.Uni;

public interface CreateQueueRequestOperation {
    Uni<Boolean> execute(Long clientId, Long tenantId, Long tenantDeploymentId, String idempotencyKey);
}
