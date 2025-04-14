package com.omgservers.service.shard.pool.impl.service.poolService.impl.method.poolContainer;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.shard.pool.poolContainer.SyncPoolContainerRequest;
import com.omgservers.schema.shard.pool.poolContainer.SyncPoolContainerResponse;
import io.smallrye.mutiny.Uni;

public interface SyncPoolContainerMethod {
    Uni<SyncPoolContainerResponse> execute(ShardModel shardModel, SyncPoolContainerRequest request);
}
