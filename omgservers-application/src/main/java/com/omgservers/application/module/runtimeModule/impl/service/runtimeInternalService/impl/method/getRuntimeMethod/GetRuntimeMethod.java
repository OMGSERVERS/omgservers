package com.omgservers.application.module.runtimeModule.impl.service.runtimeInternalService.impl.method.getRuntimeMethod;

import com.omgservers.dto.runtimeModule.GetRuntimeShardRequest;
import com.omgservers.dto.runtimeModule.GetRuntimeInternalResponse;
import io.smallrye.mutiny.Uni;

public interface GetRuntimeMethod {
    Uni<GetRuntimeInternalResponse> getRuntime(GetRuntimeShardRequest request);
}
