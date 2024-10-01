package com.omgservers.service.handler.operation;

import io.smallrye.mutiny.Uni;

public interface DeleteTenantMatchmakerRequestsByTenantDeploymentIdOperation {
    Uni<Void> execute(Long tenantId, Long tenantDeploymentId);
}
