package com.omgservers.service.shard.pool.impl.service.poolService.impl.method.poolState;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.shard.pool.poolState.UpdatePoolStateRequest;
import com.omgservers.schema.shard.pool.poolState.UpdatePoolStateResponse;
import io.smallrye.mutiny.Uni;

public interface UpdatePoolStateMethod {
    Uni<UpdatePoolStateResponse> execute(ShardModel shardModel, UpdatePoolStateRequest request);
}
