package com.omgservers.service.shard.root.impl.service.rootService.impl.method.rootEntityRef;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.module.root.rootEntityRef.GetRootEntityRefRequest;
import com.omgservers.schema.module.root.rootEntityRef.GetRootEntityRefResponse;
import io.smallrye.mutiny.Uni;

public interface GetRootEntityRefMethod {
    Uni<GetRootEntityRefResponse> execute(ShardModel shardModel, GetRootEntityRefRequest request);
}
