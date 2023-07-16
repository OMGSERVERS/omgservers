package com.omgservers.application.module.runtimeModule.impl.service.runtimeInternalService.impl.method.getRuntimeMethod;

import com.omgservers.application.module.runtimeModule.impl.service.runtimeInternalService.request.GetRuntimeInternalRequest;
import com.omgservers.application.module.runtimeModule.impl.service.runtimeInternalService.response.GetRuntimeInternalResponse;
import io.smallrye.mutiny.Uni;

public interface GetRuntimeMethod {
    Uni<GetRuntimeInternalResponse> getRuntime(GetRuntimeInternalRequest request);
}
