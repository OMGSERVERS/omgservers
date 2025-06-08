package com.omgservers.service.operation.tenant;

import io.smallrye.mutiny.Uni;

public interface CreateOpenDeploymentTenantStageCommandOperation {
    Uni<Boolean> execute(Long tenantId,
                         Long tenantStageId,
                         Long deploymentId);

    Uni<Boolean> executeFailSafe(Long tenantId,
                                 Long tenantStageId,
                                 Long deploymentId);
}
