package com.omgservers.service.handler.operation.test;

import io.smallrye.mutiny.Uni;

public interface DeleteTenantFilesArchivesByTenantVersionIdOperation {
    Uni<Void> execute(Long tenantId, Long tenantVersionId);
}
