package com.omgservers.service.module.runtime.impl.service.runtimeService.impl.method.runtimePoolServerContainerRef.getRuntimePoolServerContainerRef;

import com.omgservers.model.dto.runtime.poolServerContainerRef.GetRuntimePoolServerContainerRefRequest;
import com.omgservers.model.dto.runtime.poolServerContainerRef.GetRuntimePoolServerContainerRefResponse;
import io.smallrye.mutiny.Uni;

public interface GetRuntimePoolServerContainerRefMethod {
    Uni<GetRuntimePoolServerContainerRefResponse> getRuntimePoolServerContainerRef(
            GetRuntimePoolServerContainerRefRequest request);
}
