package com.omgservers.service.operation.tenant;

import io.smallrye.mutiny.Uni;

public interface FindAndDeleteTenantDeploymentRefOperation {
    Uni<Void> execute(Long tenantId, Long deploymentId);
}
