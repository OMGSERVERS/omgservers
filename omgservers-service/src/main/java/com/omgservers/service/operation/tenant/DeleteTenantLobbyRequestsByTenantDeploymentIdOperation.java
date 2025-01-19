package com.omgservers.service.operation.tenant;

import io.smallrye.mutiny.Uni;

public interface DeleteTenantLobbyRequestsByTenantDeploymentIdOperation {
    Uni<Void> execute(Long tenantId, Long tenantDeploymentId);
}
