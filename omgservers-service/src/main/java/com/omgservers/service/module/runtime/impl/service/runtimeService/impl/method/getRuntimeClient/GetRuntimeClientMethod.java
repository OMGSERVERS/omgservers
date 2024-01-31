package com.omgservers.service.module.runtime.impl.service.runtimeService.impl.method.getRuntimeClient;

import com.omgservers.model.dto.runtime.GetRuntimeClientRequest;
import com.omgservers.model.dto.runtime.GetRuntimeClientResponse;
import io.smallrye.mutiny.Uni;

public interface GetRuntimeClientMethod {
    Uni<GetRuntimeClientResponse> getRuntimeClient(GetRuntimeClientRequest request);
}
