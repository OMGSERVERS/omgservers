package com.omgservers.service.shard.root.impl.service.rootService.impl.method.root;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.shard.root.root.SyncRootRequest;
import com.omgservers.schema.shard.root.root.SyncRootResponse;
import io.smallrye.mutiny.Uni;

public interface SyncRootMethod {
    Uni<SyncRootResponse> execute(ShardModel shardModel, SyncRootRequest request);
}
