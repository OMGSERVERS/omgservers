package com.omgservers.service.operation.alias;

import io.smallrye.mutiny.Uni;

public interface CreateTenantStageAliasOperation {
    Uni<CreateTenantStageAliasResult> execute(Long tenantId,
                                              Long tenantProjectId,
                                              Long tenantStageId,
                                              String aliasValue);
}
