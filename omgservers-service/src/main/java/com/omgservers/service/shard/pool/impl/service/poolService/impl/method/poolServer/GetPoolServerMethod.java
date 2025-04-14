package com.omgservers.service.shard.pool.impl.service.poolService.impl.method.poolServer;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.shard.pool.poolServer.GetPoolServerRequest;
import com.omgservers.schema.shard.pool.poolServer.GetPoolServerResponse;
import io.smallrye.mutiny.Uni;

public interface GetPoolServerMethod {
    Uni<GetPoolServerResponse> execute(ShardModel shardModel, GetPoolServerRequest request);
}
