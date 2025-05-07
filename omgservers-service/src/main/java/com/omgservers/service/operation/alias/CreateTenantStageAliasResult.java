package com.omgservers.service.operation.alias;

import com.omgservers.schema.model.alias.AliasModel;

public record CreateTenantStageAliasResult(AliasModel alias, Boolean created) {
}
