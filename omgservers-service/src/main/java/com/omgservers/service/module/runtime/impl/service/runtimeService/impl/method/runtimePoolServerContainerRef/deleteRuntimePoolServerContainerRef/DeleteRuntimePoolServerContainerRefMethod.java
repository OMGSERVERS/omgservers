package com.omgservers.service.module.runtime.impl.service.runtimeService.impl.method.runtimePoolServerContainerRef.deleteRuntimePoolServerContainerRef;

import com.omgservers.model.dto.runtime.poolServerContainerRef.DeleteRuntimePoolServerContainerRefRequest;
import com.omgservers.model.dto.runtime.poolServerContainerRef.DeleteRuntimePoolServerContainerRefResponse;
import io.smallrye.mutiny.Uni;

public interface DeleteRuntimePoolServerContainerRefMethod {
    Uni<DeleteRuntimePoolServerContainerRefResponse> deleteRuntimePoolServerContainerRef(
            DeleteRuntimePoolServerContainerRefRequest request);
}
