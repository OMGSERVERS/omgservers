package com.omgservers.service.shard.pool.impl.service.poolService.impl.method.pool;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.shard.pool.pool.GetPoolRequest;
import com.omgservers.schema.shard.pool.pool.GetPoolResponse;
import io.smallrye.mutiny.Uni;

public interface GetPoolMethod {
    Uni<GetPoolResponse> execute(ShardModel shardModel, GetPoolRequest request);
}
