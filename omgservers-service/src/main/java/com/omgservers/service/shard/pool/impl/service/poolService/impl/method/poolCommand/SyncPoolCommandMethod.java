package com.omgservers.service.shard.pool.impl.service.poolService.impl.method.poolCommand;

import com.omgservers.schema.module.pool.poolCommand.SyncPoolCommandRequest;
import com.omgservers.schema.module.pool.poolCommand.SyncPoolCommandResponse;
import io.smallrye.mutiny.Uni;

public interface SyncPoolCommandMethod {
    Uni<SyncPoolCommandResponse> execute(SyncPoolCommandRequest request);
}
