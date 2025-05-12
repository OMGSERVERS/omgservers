package com.omgservers.service.operation.alias;

import com.omgservers.schema.model.alias.AliasModel;
import io.smallrye.mutiny.Uni;

public interface FindUserAliasOperation {
    Uni<AliasModel> execute(final String aliasValue);
}
