package com.omgservers.service.shard.runtime.impl.service.runtimeService.impl.method.runtime;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.shard.runtime.runtime.SyncRuntimeRequest;
import com.omgservers.schema.shard.runtime.runtime.SyncRuntimeResponse;
import io.smallrye.mutiny.Uni;

public interface SyncRuntimeMethod {
    Uni<SyncRuntimeResponse> execute(ShardModel shardModel, SyncRuntimeRequest request);
}
