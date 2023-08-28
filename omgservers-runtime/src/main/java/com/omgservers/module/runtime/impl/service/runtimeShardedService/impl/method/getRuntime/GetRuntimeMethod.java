package com.omgservers.module.runtime.impl.service.runtimeShardedService.impl.method.getRuntime;

import com.omgservers.dto.runtime.GetRuntimeShardedRequest;
import com.omgservers.dto.runtime.GetRuntimeShardedResponse;
import io.smallrye.mutiny.Uni;

public interface GetRuntimeMethod {
    Uni<GetRuntimeShardedResponse> getRuntime(GetRuntimeShardedRequest request);
}
