package com.omgservers.service.shard.root.impl.service.rootService.impl.method.rootEntityRef;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.shard.root.rootEntityRef.FindRootEntityRefRequest;
import com.omgservers.schema.shard.root.rootEntityRef.FindRootEntityRefResponse;
import io.smallrye.mutiny.Uni;

public interface FindRootEntityRefMethod {
    Uni<FindRootEntityRefResponse> execute(ShardModel shardModel, FindRootEntityRefRequest request);
}
