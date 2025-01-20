package com.omgservers.service.operation.alias;

import com.omgservers.schema.model.alias.AliasModel;
import io.smallrye.mutiny.Uni;

public interface CreateTenantAliasOperation {
    Uni<AliasModel> execute(Long tenantId, String aliasValue);
}
