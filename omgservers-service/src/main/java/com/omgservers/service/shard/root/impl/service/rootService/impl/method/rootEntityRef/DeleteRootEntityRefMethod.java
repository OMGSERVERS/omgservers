package com.omgservers.service.shard.root.impl.service.rootService.impl.method.rootEntityRef;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.module.root.rootEntityRef.DeleteRootEntityRefRequest;
import com.omgservers.schema.module.root.rootEntityRef.DeleteRootEntityRefResponse;
import io.smallrye.mutiny.Uni;

public interface DeleteRootEntityRefMethod {
    Uni<DeleteRootEntityRefResponse> execute(ShardModel shardModel, DeleteRootEntityRefRequest request);
}
