package com.omgservers.service.shard.alias.impl.service.aliasService.impl.method;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.module.alias.FindAliasRequest;
import com.omgservers.schema.module.alias.FindAliasResponse;
import io.smallrye.mutiny.Uni;

public interface FindAliasMethod {
    Uni<FindAliasResponse> execute(ShardModel shardModel, FindAliasRequest request);
}
