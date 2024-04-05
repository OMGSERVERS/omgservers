package com.omgservers.service.module.runtime.impl.service.runtimeService.impl.method.runtimePoolServerContainerRef.findRuntimePoolServerContainerRef;

import com.omgservers.model.dto.runtime.poolServerContainerRef.FindRuntimePoolServerContainerRefRequest;
import com.omgservers.model.dto.runtime.poolServerContainerRef.FindRuntimePoolServerContainerRefResponse;
import io.smallrye.mutiny.Uni;

public interface FindRuntimePoolServerContainerRefMethod {
    Uni<FindRuntimePoolServerContainerRefResponse> findRuntimePoolServerContainerRef(
            FindRuntimePoolServerContainerRefRequest request);
}
