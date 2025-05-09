package com.omgservers.service.shard.runtime.impl.service.runtimeService.impl.method.runtimeCommand;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.shard.runtime.runtimeCommand.SyncRuntimeCommandRequest;
import com.omgservers.schema.shard.runtime.runtimeCommand.SyncRuntimeCommandResponse;
import io.smallrye.mutiny.Uni;

public interface SyncRuntimeCommandMethod {
    Uni<SyncRuntimeCommandResponse> execute(ShardModel shardModel, SyncRuntimeCommandRequest request);
}
