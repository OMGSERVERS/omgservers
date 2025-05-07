package com.omgservers.service.operation.alias;

import io.smallrye.mutiny.Uni;

public interface CreateTenantAliasOperation {
    Uni<CreateTenantAliasResult> execute(Long tenantId, String aliasValue);
}
