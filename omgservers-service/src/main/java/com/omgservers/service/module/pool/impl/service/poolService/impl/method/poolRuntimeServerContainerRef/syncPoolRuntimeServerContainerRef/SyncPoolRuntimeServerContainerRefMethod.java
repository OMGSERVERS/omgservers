package com.omgservers.service.module.pool.impl.service.poolService.impl.method.poolRuntimeServerContainerRef.syncPoolRuntimeServerContainerRef;

import com.omgservers.model.dto.pool.poolRuntimeServerContainerRef.SyncPoolRuntimeServerContainerRefRequest;
import com.omgservers.model.dto.pool.poolRuntimeServerContainerRef.SyncPoolRuntimeServerContainerRefResponse;
import io.smallrye.mutiny.Uni;

public interface SyncPoolRuntimeServerContainerRefMethod {
    Uni<SyncPoolRuntimeServerContainerRefResponse> syncPoolRuntimeServerContainerRef(
            SyncPoolRuntimeServerContainerRefRequest request);
}
