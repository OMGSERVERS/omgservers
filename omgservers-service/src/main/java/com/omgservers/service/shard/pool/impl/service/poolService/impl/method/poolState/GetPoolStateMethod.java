package com.omgservers.service.shard.pool.impl.service.poolService.impl.method.poolState;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.module.pool.poolState.GetPoolStateRequest;
import com.omgservers.schema.module.pool.poolState.GetPoolStateResponse;
import io.smallrye.mutiny.Uni;

public interface GetPoolStateMethod {
    Uni<GetPoolStateResponse> execute(ShardModel shardModel, GetPoolStateRequest request);
}
