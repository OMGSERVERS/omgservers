package com.omgservers.module.runtime.impl.service.runtimeShardedService.impl.method.getRuntime;

import com.omgservers.dto.runtime.GetRuntimeShardedRequest;
import com.omgservers.dto.runtime.GetRuntimeInternalResponse;
import io.smallrye.mutiny.Uni;

public interface GetRuntimeMethod {
    Uni<GetRuntimeInternalResponse> getRuntime(GetRuntimeShardedRequest request);
}
