package com.omgservers.service.module.runtime.impl.service.runtimeService.impl.method.runtimePoolContainerRef;

import com.omgservers.schema.module.runtime.poolContainerRef.DeleteRuntimePoolContainerRefRequest;
import com.omgservers.schema.module.runtime.poolContainerRef.DeleteRuntimePoolContainerRefResponse;
import io.smallrye.mutiny.Uni;

public interface DeleteRuntimePoolContainerRefMethod {
    Uni<DeleteRuntimePoolContainerRefResponse> execute(DeleteRuntimePoolContainerRefRequest request);
}
