package com.omgservers.service.shard.pool.impl.service.poolService.impl.method.poolContainer;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.module.pool.poolContainer.GetPoolContainerRequest;
import com.omgservers.schema.module.pool.poolContainer.GetPoolContainerResponse;
import io.smallrye.mutiny.Uni;

public interface GetPoolContainerMethod {
    Uni<GetPoolContainerResponse> execute(ShardModel shardModel, GetPoolContainerRequest request);
}
