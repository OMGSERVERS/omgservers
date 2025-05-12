package com.omgservers.service.operation.alias;

import com.omgservers.schema.model.alias.AliasModel;
import io.smallrye.mutiny.Uni;

public interface CreateRootAliasOperation {
    Uni<AliasModel> execute(Long rootId);
}
