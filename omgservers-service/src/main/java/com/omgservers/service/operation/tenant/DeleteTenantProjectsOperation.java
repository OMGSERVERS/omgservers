package com.omgservers.service.operation.tenant;

import io.smallrye.mutiny.Uni;

public interface DeleteTenantProjectsOperation {
    Uni<Void> execute(Long tenantId);
}
