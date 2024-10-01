package com.omgservers.service.handler.operation;

import io.smallrye.mutiny.Uni;

public interface DeleteTenantProjectsOperation {
    Uni<Void> execute(Long tenantId);
}
