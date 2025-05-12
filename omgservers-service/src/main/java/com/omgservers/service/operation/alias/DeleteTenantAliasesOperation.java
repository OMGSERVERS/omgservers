package com.omgservers.service.operation.alias;

import io.smallrye.mutiny.Uni;

public interface DeleteTenantAliasesOperation {
    Uni<Void> execute(Long entityId);
}
