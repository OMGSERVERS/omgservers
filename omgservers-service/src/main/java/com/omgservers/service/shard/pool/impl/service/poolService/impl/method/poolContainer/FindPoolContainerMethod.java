package com.omgservers.service.shard.pool.impl.service.poolService.impl.method.poolContainer;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.shard.pool.poolContainer.FindPoolContainerRequest;
import com.omgservers.schema.shard.pool.poolContainer.FindPoolContainerResponse;
import io.smallrye.mutiny.Uni;

public interface FindPoolContainerMethod {
    Uni<FindPoolContainerResponse> execute(ShardModel shardModel, FindPoolContainerRequest request);
}
