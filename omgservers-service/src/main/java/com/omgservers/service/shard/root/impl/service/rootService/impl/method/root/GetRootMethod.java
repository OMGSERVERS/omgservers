package com.omgservers.service.shard.root.impl.service.rootService.impl.method.root;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.shard.root.root.GetRootRequest;
import com.omgservers.schema.shard.root.root.GetRootResponse;
import io.smallrye.mutiny.Uni;

public interface GetRootMethod {
    Uni<GetRootResponse> execute(ShardModel shardModel, GetRootRequest request);
}
