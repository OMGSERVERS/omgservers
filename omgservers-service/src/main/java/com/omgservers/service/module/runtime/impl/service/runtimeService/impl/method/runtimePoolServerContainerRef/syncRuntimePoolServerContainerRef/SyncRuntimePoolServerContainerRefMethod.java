package com.omgservers.service.module.runtime.impl.service.runtimeService.impl.method.runtimePoolServerContainerRef.syncRuntimePoolServerContainerRef;

import com.omgservers.model.dto.runtime.poolServerContainerRef.SyncRuntimePoolServerContainerRefRequest;
import com.omgservers.model.dto.runtime.poolServerContainerRef.SyncRuntimePoolServerContainerRefResponse;
import io.smallrye.mutiny.Uni;

public interface SyncRuntimePoolServerContainerRefMethod {
    Uni<SyncRuntimePoolServerContainerRefResponse> syncRuntimePoolServerContainerRef(
            SyncRuntimePoolServerContainerRefRequest request);
}
