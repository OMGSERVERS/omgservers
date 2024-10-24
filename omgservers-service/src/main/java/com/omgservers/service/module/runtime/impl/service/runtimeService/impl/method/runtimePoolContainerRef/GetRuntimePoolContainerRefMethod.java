package com.omgservers.service.module.runtime.impl.service.runtimeService.impl.method.runtimePoolContainerRef;

import com.omgservers.schema.module.runtime.poolContainerRef.GetRuntimePoolContainerRefRequest;
import com.omgservers.schema.module.runtime.poolContainerRef.GetRuntimePoolContainerRefResponse;
import io.smallrye.mutiny.Uni;

public interface GetRuntimePoolContainerRefMethod {
    Uni<GetRuntimePoolContainerRefResponse> execute(GetRuntimePoolContainerRefRequest request);
}
