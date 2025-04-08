package com.omgservers.service.shard.alias.impl.service.aliasService.impl.method;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.module.alias.GetAliasRequest;
import com.omgservers.schema.module.alias.GetAliasResponse;
import io.smallrye.mutiny.Uni;

public interface GetAliasMethod {
    Uni<GetAliasResponse> execute(ShardModel shardModel, GetAliasRequest request);
}
