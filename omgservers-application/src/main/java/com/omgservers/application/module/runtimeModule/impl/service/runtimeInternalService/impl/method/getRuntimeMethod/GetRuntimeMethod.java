package com.omgservers.application.module.runtimeModule.impl.service.runtimeInternalService.impl.method.getRuntimeMethod;

import com.omgservers.dto.runtimeModule.GetRuntimeInternalRequest;
import com.omgservers.dto.runtimeModule.GetRuntimeInternalResponse;
import io.smallrye.mutiny.Uni;

public interface GetRuntimeMethod {
    Uni<GetRuntimeInternalResponse> getRuntime(GetRuntimeInternalRequest request);
}
