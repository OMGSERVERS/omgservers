package com.omgservers.service.module.pool.impl.service.poolService.impl.method.poolRuntimeServerContainerRef.getPoolRuntimeServerContainerRef;

import com.omgservers.model.dto.pool.poolRuntimeServerContainerRef.GetPoolRuntimeServerContainerRefRequest;
import com.omgservers.model.dto.pool.poolRuntimeServerContainerRef.GetPoolRuntimeServerContainerRefResponse;
import io.smallrye.mutiny.Uni;

public interface GetPoolRuntimeServerContainerRefMethod {
    Uni<GetPoolRuntimeServerContainerRefResponse> getPoolRuntimeServerContainerRef(
            GetPoolRuntimeServerContainerRefRequest request);
}
