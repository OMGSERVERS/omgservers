package com.omgservers.service.operation.tenant;

import io.smallrye.mutiny.Uni;

public interface DeleteTenantMatchmakerRequestsByTenantDeploymentIdOperation {
    Uni<Void> execute(Long tenantId, Long tenantDeploymentId);
}
