package com.omgservers.service.module.runtime.impl.service.runtimeService.impl.method.findRuntimeServerContainerRef;

import com.omgservers.model.dto.runtime.serverRuntimeRef.FindRuntimeServerContainerRefRequest;
import com.omgservers.model.dto.runtime.serverRuntimeRef.FindRuntimeServerContainerRefResponse;
import io.smallrye.mutiny.Uni;

public interface FindRuntimeServerContainerRefMethod {
    Uni<FindRuntimeServerContainerRefResponse> findRuntimeServerContainerRef(
            FindRuntimeServerContainerRefRequest request);
}
