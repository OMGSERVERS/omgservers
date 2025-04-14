package com.omgservers.service.shard.pool.impl.service.poolService.impl.method.poolServer;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.shard.pool.poolServer.DeletePoolServerRequest;
import com.omgservers.schema.shard.pool.poolServer.DeletePoolServerResponse;
import io.smallrye.mutiny.Uni;

public interface DeletePoolServerMethod {
    Uni<DeletePoolServerResponse> execute(ShardModel shardModel, DeletePoolServerRequest request);
}
