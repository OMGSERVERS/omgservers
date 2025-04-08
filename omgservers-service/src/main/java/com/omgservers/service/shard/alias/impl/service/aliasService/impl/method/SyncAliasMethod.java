package com.omgservers.service.shard.alias.impl.service.aliasService.impl.method;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.module.alias.SyncAliasRequest;
import com.omgservers.schema.module.alias.SyncAliasResponse;
import io.smallrye.mutiny.Uni;

public interface SyncAliasMethod {
    Uni<SyncAliasResponse> execute(ShardModel shardModel, SyncAliasRequest request);
}
