package com.omgservers.service.handler.operation;

import io.smallrye.mutiny.Uni;

public interface DeleteTenantImageRefByTenantVersionIdOperation {
    Uni<Void> execute(Long tenantId, Long tenantVersionId);
}
