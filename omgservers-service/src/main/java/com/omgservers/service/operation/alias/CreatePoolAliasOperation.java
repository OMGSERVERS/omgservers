package com.omgservers.service.operation.alias;

import com.omgservers.schema.model.alias.AliasModel;
import io.smallrye.mutiny.Uni;

public interface CreatePoolAliasOperation {
    Uni<AliasModel> execute(Long poolId, String aliasValue);
}
