package com.omgservers.service.module.runtime.impl.service.runtimeService.impl.method.runtimeServerContainerRef.deleteRuntimeServerContainerRef;

import com.omgservers.model.dto.runtime.serverRuntimeRef.DeleteRuntimeServerContainerRefRequest;
import com.omgservers.model.dto.runtime.serverRuntimeRef.DeleteRuntimeServerContainerRefResponse;
import io.smallrye.mutiny.Uni;

public interface DeleteRuntimeServerContainerRefMethod {
    Uni<DeleteRuntimeServerContainerRefResponse> deleteRuntimeServerContainerRef(
            DeleteRuntimeServerContainerRefRequest request);
}
