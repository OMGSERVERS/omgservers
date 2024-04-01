package com.omgservers.service.module.pool.impl.service.poolService.impl.method.poolRuntimeServerContainerRef.findPoolRuntimeServerContainerRef;

import com.omgservers.model.dto.pool.poolRuntimeServerContainerRef.FindPoolRuntimeServerContainerRefRequest;
import com.omgservers.model.dto.pool.poolRuntimeServerContainerRef.FindPoolRuntimeServerContainerRefResponse;
import io.smallrye.mutiny.Uni;

public interface FindPoolRuntimeServerContainerRefMethod {
    Uni<FindPoolRuntimeServerContainerRefResponse> findPoolRuntimeServerContainerRef(
            FindPoolRuntimeServerContainerRefRequest request);
}
