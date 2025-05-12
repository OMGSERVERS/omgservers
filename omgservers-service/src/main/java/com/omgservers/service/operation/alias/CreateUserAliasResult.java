package com.omgservers.service.operation.alias;

import com.omgservers.schema.model.alias.AliasModel;

public record CreateUserAliasResult(AliasModel alias, Boolean created) {
}
