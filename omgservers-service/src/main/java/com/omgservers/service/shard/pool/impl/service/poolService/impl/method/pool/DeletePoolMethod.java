package com.omgservers.service.shard.pool.impl.service.poolService.impl.method.pool;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.module.pool.pool.DeletePoolRequest;
import com.omgservers.schema.module.pool.pool.DeletePoolResponse;
import io.smallrye.mutiny.Uni;

public interface DeletePoolMethod {
    Uni<DeletePoolResponse> execute(ShardModel shardModel, DeletePoolRequest request);
}
