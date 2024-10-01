package com.omgservers.service.handler.operation;

import io.smallrye.mutiny.Uni;

public interface DeleteTenantProjectPermissionsOperation {
    Uni<Void> execute(Long tenantId, Long tenantProjectId);
}
