package com.omgservers.service.shard.pool.impl.service.poolService.impl.method.poolCommand;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.shard.pool.poolCommand.SyncPoolCommandRequest;
import com.omgservers.schema.shard.pool.poolCommand.SyncPoolCommandResponse;
import io.smallrye.mutiny.Uni;

public interface SyncPoolCommandMethod {
    Uni<SyncPoolCommandResponse> execute(ShardModel shardModel, SyncPoolCommandRequest request);
}
