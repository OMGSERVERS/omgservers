package com.omgservers.service.module.runtime.impl.service.runtimeService.impl.method.runtime.getRuntime;

import com.omgservers.schema.module.runtime.GetRuntimeRequest;
import com.omgservers.schema.module.runtime.GetRuntimeResponse;
import io.smallrye.mutiny.Uni;

public interface GetRuntimeMethod {
    Uni<GetRuntimeResponse> getRuntime(GetRuntimeRequest request);
}
