package com.omgservers.service.operation.tenant;

import io.smallrye.mutiny.Uni;

public interface DeleteTenantLobbyResourcesByTenantDeploymentIdOperation {
    Uni<Void> execute(Long tenantId, Long tenantDeploymentId);
}
