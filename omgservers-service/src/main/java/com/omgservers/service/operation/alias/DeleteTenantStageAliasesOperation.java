package com.omgservers.service.operation.alias;

import io.smallrye.mutiny.Uni;

public interface DeleteTenantStageAliasesOperation {
    Uni<Void> execute(Long tenantId, Long tenantStageId);
}
