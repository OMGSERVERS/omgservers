package com.omgservers.service.shard.root.impl.service.rootService.impl.method.root;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.shard.root.root.DeleteRootRequest;
import com.omgservers.schema.shard.root.root.DeleteRootResponse;
import io.smallrye.mutiny.Uni;

public interface DeleteRootMethod {
    Uni<DeleteRootResponse> execute(ShardModel shardModel, DeleteRootRequest request);
}
