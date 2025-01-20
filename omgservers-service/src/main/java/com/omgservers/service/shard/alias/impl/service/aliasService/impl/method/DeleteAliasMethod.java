package com.omgservers.service.shard.alias.impl.service.aliasService.impl.method;

import com.omgservers.schema.module.alias.DeleteAliasRequest;
import com.omgservers.schema.module.alias.DeleteAliasResponse;
import io.smallrye.mutiny.Uni;

public interface DeleteAliasMethod {
    Uni<DeleteAliasResponse> execute(DeleteAliasRequest request);
}
