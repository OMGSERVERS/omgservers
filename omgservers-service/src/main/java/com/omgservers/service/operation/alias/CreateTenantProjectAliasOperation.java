package com.omgservers.service.operation.alias;

import io.smallrye.mutiny.Uni;

public interface CreateTenantProjectAliasOperation {
    Uni<CreateTenantProjectAliasResult> execute(Long tenantId,
                                                Long tenantProjectId,
                                                String aliasValue);
}
