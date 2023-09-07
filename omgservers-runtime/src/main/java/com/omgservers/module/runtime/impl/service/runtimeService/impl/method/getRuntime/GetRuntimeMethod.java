package com.omgservers.module.runtime.impl.service.runtimeService.impl.method.getRuntime;

import com.omgservers.dto.runtime.GetRuntimeRequest;
import com.omgservers.dto.runtime.GetRuntimeResponse;
import io.smallrye.mutiny.Uni;

public interface GetRuntimeMethod {
    Uni<GetRuntimeResponse> getRuntime(GetRuntimeRequest request);
}
