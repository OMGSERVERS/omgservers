package com.omgservers.service.shard.pool.impl.service.poolService.impl.method.poolCommand;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.shard.pool.poolCommand.ViewPoolCommandRequest;
import com.omgservers.schema.shard.pool.poolCommand.ViewPoolCommandResponse;
import io.smallrye.mutiny.Uni;

public interface ViewPoolCommandsMethod {
    Uni<ViewPoolCommandResponse> execute(ShardModel shardModel, ViewPoolCommandRequest request);
}
