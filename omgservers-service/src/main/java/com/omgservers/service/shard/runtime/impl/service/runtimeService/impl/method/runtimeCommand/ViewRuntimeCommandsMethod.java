package com.omgservers.service.shard.runtime.impl.service.runtimeService.impl.method.runtimeCommand;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.module.runtime.runtimeCommand.ViewRuntimeCommandsRequest;
import com.omgservers.schema.module.runtime.runtimeCommand.ViewRuntimeCommandsResponse;
import io.smallrye.mutiny.Uni;

public interface ViewRuntimeCommandsMethod {
    Uni<ViewRuntimeCommandsResponse> execute(ShardModel shardModel, ViewRuntimeCommandsRequest request);
}
