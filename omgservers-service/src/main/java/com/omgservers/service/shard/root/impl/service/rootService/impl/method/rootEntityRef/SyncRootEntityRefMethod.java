package com.omgservers.service.shard.root.impl.service.rootService.impl.method.rootEntityRef;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.shard.root.rootEntityRef.SyncRootEntityRefRequest;
import com.omgservers.schema.shard.root.rootEntityRef.SyncRootEntityRefResponse;
import io.smallrye.mutiny.Uni;

public interface SyncRootEntityRefMethod {
    Uni<SyncRootEntityRefResponse> execute(ShardModel shardModel, SyncRootEntityRefRequest request);
}
