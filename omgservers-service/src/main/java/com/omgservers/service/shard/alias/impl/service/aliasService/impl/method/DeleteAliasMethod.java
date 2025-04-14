package com.omgservers.service.shard.alias.impl.service.aliasService.impl.method;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.shard.alias.DeleteAliasRequest;
import com.omgservers.schema.shard.alias.DeleteAliasResponse;
import io.smallrye.mutiny.Uni;

public interface DeleteAliasMethod {
    Uni<DeleteAliasResponse> execute(ShardModel shardModel, DeleteAliasRequest request);
}
