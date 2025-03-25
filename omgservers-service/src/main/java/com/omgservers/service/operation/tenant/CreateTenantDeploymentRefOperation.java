package com.omgservers.service.operation.tenant;

import io.smallrye.mutiny.Uni;

public interface CreateTenantDeploymentRefOperation {
    Uni<Boolean> execute(Long tenantId,
                         Long tenantStageId,
                         Long tenantVersionId,
                         Long deploymentId,
                         String idempotencyKey);
}
