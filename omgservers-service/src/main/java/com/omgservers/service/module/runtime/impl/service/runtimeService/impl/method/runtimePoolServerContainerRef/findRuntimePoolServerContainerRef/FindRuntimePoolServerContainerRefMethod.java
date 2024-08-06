package com.omgservers.service.module.runtime.impl.service.runtimeService.impl.method.runtimePoolServerContainerRef.findRuntimePoolServerContainerRef;

import com.omgservers.schema.module.runtime.poolServerContainerRef.FindRuntimePoolServerContainerRefRequest;
import com.omgservers.schema.module.runtime.poolServerContainerRef.FindRuntimePoolServerContainerRefResponse;
import io.smallrye.mutiny.Uni;

public interface FindRuntimePoolServerContainerRefMethod {
    Uni<FindRuntimePoolServerContainerRefResponse> findRuntimePoolServerContainerRef(
            FindRuntimePoolServerContainerRefRequest request);
}
