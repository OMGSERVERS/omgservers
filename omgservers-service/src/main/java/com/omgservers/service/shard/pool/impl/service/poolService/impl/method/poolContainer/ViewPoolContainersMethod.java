package com.omgservers.service.shard.pool.impl.service.poolService.impl.method.poolContainer;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.shard.pool.poolContainer.ViewPoolContainersRequest;
import com.omgservers.schema.shard.pool.poolContainer.ViewPoolContainersResponse;
import io.smallrye.mutiny.Uni;

public interface ViewPoolContainersMethod {
    Uni<ViewPoolContainersResponse> execute(ShardModel shardModel, ViewPoolContainersRequest request);
}
