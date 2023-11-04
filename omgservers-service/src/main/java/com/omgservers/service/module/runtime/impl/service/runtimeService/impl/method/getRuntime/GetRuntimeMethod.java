package com.omgservers.service.module.runtime.impl.service.runtimeService.impl.method.getRuntime;

import com.omgservers.model.dto.runtime.GetRuntimeRequest;
import com.omgservers.model.dto.runtime.GetRuntimeResponse;
import io.smallrye.mutiny.Uni;

public interface GetRuntimeMethod {
    Uni<GetRuntimeResponse> getRuntime(GetRuntimeRequest request);
}
