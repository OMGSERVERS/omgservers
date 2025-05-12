package com.omgservers.service.operation.alias;

import io.smallrye.mutiny.Uni;

public interface DeleteTenantProjectAliasesOperation {
    Uni<Void> execute(Long tenantId, Long tenantProjectId);
}
