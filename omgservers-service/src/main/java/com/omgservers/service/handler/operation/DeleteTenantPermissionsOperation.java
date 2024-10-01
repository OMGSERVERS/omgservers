package com.omgservers.service.handler.operation;

import io.smallrye.mutiny.Uni;

public interface DeleteTenantPermissionsOperation {
    Uni<Void> execute(Long tenantId);
}
