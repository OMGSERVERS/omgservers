package com.omgservers.service.operation.tenant;

import io.smallrye.mutiny.Uni;

public interface DeleteTenantPermissionsOperation {
    Uni<Void> execute(Long tenantId);
}
