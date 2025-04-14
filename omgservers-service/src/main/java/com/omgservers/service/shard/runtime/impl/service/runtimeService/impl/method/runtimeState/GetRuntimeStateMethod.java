package com.omgservers.service.shard.runtime.impl.service.runtimeService.impl.method.runtimeState;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.shard.runtime.runtimeState.GetRuntimeStateRequest;
import com.omgservers.schema.shard.runtime.runtimeState.GetRuntimeStateResponse;
import io.smallrye.mutiny.Uni;

public interface GetRuntimeStateMethod {
    Uni<GetRuntimeStateResponse> execute(ShardModel shardModel, GetRuntimeStateRequest request);
}
