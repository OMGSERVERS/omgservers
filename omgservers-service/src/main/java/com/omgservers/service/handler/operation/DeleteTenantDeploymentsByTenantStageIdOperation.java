package com.omgservers.service.handler.operation;

import io.smallrye.mutiny.Uni;

public interface DeleteTenantDeploymentsByTenantStageIdOperation {
    Uni<Void> execute(Long tenantId, Long tenantStageId);
}
