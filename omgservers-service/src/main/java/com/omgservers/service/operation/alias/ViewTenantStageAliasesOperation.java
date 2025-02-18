package com.omgservers.service.operation.alias;

import com.omgservers.schema.model.alias.AliasModel;
import io.smallrye.mutiny.Uni;

import java.util.List;

public interface ViewTenantStageAliasesOperation {
    Uni<List<AliasModel>> execute(Long tenantId, Long tenantStageId);
}
