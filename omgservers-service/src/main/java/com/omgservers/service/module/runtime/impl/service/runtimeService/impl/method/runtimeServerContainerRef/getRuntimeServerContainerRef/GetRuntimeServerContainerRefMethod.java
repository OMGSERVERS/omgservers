package com.omgservers.service.module.runtime.impl.service.runtimeService.impl.method.runtimeServerContainerRef.getRuntimeServerContainerRef;

import com.omgservers.model.dto.runtime.serverRuntimeRef.GetRuntimeServerContainerRefRequest;
import com.omgservers.model.dto.runtime.serverRuntimeRef.GetRuntimeServerContainerRefResponse;
import io.smallrye.mutiny.Uni;

public interface GetRuntimeServerContainerRefMethod {
    Uni<GetRuntimeServerContainerRefResponse> getRuntimeServerContainerRef(GetRuntimeServerContainerRefRequest request);
}
