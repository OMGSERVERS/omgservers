package com.omgservers.service.shard.pool.impl.service.poolService.impl.method.poolContainer;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.shard.pool.poolContainer.DeletePoolContainerRequest;
import com.omgservers.schema.shard.pool.poolContainer.DeletePoolContainerResponse;
import io.smallrye.mutiny.Uni;

public interface DeletePoolContainerMethod {
    Uni<DeletePoolContainerResponse> execute(ShardModel shardModel, DeletePoolContainerRequest request);
}
