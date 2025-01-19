package com.omgservers.service.operation.tenant;

import io.smallrye.mutiny.Uni;

public interface DeleteTenantImagesByTenantVersionIdOperation {
    Uni<Void> execute(Long tenantId, Long tenantVersionId);
}
