package com.omgservers.service.operation.tenant;

import io.smallrye.mutiny.Uni;

public interface DeleteTenantFilesArchivesByTenantVersionIdOperation {
    Uni<Void> execute(Long tenantId, Long tenantVersionId);
}
