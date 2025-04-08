package com.omgservers.service.shard.runtime.impl.service.runtimeService.impl.method.runtime;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.module.runtime.runtime.GetRuntimeRequest;
import com.omgservers.schema.module.runtime.runtime.GetRuntimeResponse;
import io.smallrye.mutiny.Uni;

public interface GetRuntimeMethod {
    Uni<GetRuntimeResponse> execute(ShardModel shardModel, GetRuntimeRequest request);
}
