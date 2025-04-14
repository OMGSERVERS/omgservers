package com.omgservers.service.shard.pool.impl.service.poolService.impl.method.poolCommand;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.shard.pool.poolCommand.GetPoolCommandRequest;
import com.omgservers.schema.shard.pool.poolCommand.GetPoolCommandResponse;
import io.smallrye.mutiny.Uni;

public interface GetPoolCommandMethod {
    Uni<GetPoolCommandResponse> execute(ShardModel shardModel, GetPoolCommandRequest request);
}
