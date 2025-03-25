package com.omgservers.service.shard.runtime.impl.service.runtimeService.impl.method.runtimeState;

import com.omgservers.schema.module.runtime.runtimeState.GetRuntimeStateRequest;
import com.omgservers.schema.module.runtime.runtimeState.GetRuntimeStateResponse;
import io.smallrye.mutiny.Uni;

public interface GetRuntimeStateMethod {
    Uni<GetRuntimeStateResponse> execute(GetRuntimeStateRequest request);
}
