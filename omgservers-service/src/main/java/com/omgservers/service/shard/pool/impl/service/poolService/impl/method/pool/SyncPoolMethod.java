package com.omgservers.service.shard.pool.impl.service.poolService.impl.method.pool;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.shard.pool.pool.SyncPoolRequest;
import com.omgservers.schema.shard.pool.pool.SyncPoolResponse;
import io.smallrye.mutiny.Uni;

public interface SyncPoolMethod {
    Uni<SyncPoolResponse> execute(ShardModel shardModel, SyncPoolRequest request);
}
