package com.omgservers.service.module.runtime.impl.service.runtimeService.impl.method.runtimePoolServerContainerRef.deleteRuntimePoolServerContainerRef;

import com.omgservers.schema.module.runtime.poolServerContainerRef.DeleteRuntimePoolServerContainerRefRequest;
import com.omgservers.schema.module.runtime.poolServerContainerRef.DeleteRuntimePoolServerContainerRefResponse;
import io.smallrye.mutiny.Uni;

public interface DeleteRuntimePoolServerContainerRefMethod {
    Uni<DeleteRuntimePoolServerContainerRefResponse> deleteRuntimePoolServerContainerRef(
            DeleteRuntimePoolServerContainerRefRequest request);
}
