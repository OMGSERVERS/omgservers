package com.omgservers.service.shard.alias.impl.service.aliasService.impl.method;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.shard.alias.ViewAliasesRequest;
import com.omgservers.schema.shard.alias.ViewAliasesResponse;
import io.smallrye.mutiny.Uni;

public interface ViewAliasesMethod {
    Uni<ViewAliasesResponse> execute(ShardModel shardModel, ViewAliasesRequest request);
}
