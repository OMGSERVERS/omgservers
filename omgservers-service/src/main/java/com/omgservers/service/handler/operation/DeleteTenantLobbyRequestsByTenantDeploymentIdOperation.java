package com.omgservers.service.handler.operation;

import io.smallrye.mutiny.Uni;

public interface DeleteTenantLobbyRequestsByTenantDeploymentIdOperation {
    Uni<Void> execute(Long tenantId, Long tenantDeploymentId);
}
