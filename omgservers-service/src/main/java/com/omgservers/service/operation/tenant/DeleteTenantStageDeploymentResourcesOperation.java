package com.omgservers.service.operation.tenant;

import io.smallrye.mutiny.Uni;

public interface DeleteTenantStageDeploymentResourcesOperation {
    Uni<Void> execute(Long tenantId, Long tenantStageId);
}
