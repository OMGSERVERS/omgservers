package com.omgservers.service.shard.runtime.impl.service.runtimeService.impl.method.runtimeState;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.shard.runtime.runtimeState.UpdateRuntimeStateRequest;
import com.omgservers.schema.shard.runtime.runtimeState.UpdateRuntimeStateResponse;
import io.smallrye.mutiny.Uni;

public interface UpdateRuntimeStateMethod {
    Uni<UpdateRuntimeStateResponse> execute(ShardModel shardModel, UpdateRuntimeStateRequest request);
}
