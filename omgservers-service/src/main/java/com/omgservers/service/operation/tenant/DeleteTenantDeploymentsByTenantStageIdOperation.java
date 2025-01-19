package com.omgservers.service.operation.tenant;

import io.smallrye.mutiny.Uni;

public interface DeleteTenantDeploymentsByTenantStageIdOperation {
    Uni<Void> execute(Long tenantId, Long tenantStageId);
}
