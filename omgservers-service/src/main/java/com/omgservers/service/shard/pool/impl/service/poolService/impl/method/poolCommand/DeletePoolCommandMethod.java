package com.omgservers.service.shard.pool.impl.service.poolService.impl.method.poolCommand;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.shard.pool.poolCommand.DeletePoolCommandRequest;
import com.omgservers.schema.shard.pool.poolCommand.DeletePoolCommandResponse;
import io.smallrye.mutiny.Uni;

public interface DeletePoolCommandMethod {
    Uni<DeletePoolCommandResponse> execute(ShardModel shardModel, DeletePoolCommandRequest request);
}
