package com.omgservers.service.shard.pool.impl.service.poolService.impl.method.poolServer;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.shard.pool.poolServer.SyncPoolServerRequest;
import com.omgservers.schema.shard.pool.poolServer.SyncPoolServerResponse;
import io.smallrye.mutiny.Uni;

public interface SyncPoolServerMethod {
    Uni<SyncPoolServerResponse> execute(ShardModel shardModel, SyncPoolServerRequest request);
}
