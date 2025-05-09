package com.omgservers.service.shard.pool.impl.service.poolService.impl.method.poolServer;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.shard.pool.poolServer.ViewPoolServersResponse;
import com.omgservers.schema.shard.pool.poolServer.ViewPoolServersRequest;
import io.smallrye.mutiny.Uni;

public interface ViewPoolServersMethod {
    Uni<ViewPoolServersResponse> execute(ShardModel shardModel, ViewPoolServersRequest request);
}
