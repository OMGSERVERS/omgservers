package com.omgservers.service.shard.runtime.impl.service.runtimeService.impl.method.runtimePoolContainerRef;

import com.omgservers.schema.module.runtime.poolContainerRef.SyncRuntimePoolContainerRefRequest;
import com.omgservers.schema.module.runtime.poolContainerRef.SyncRuntimePoolContainerRefResponse;
import io.smallrye.mutiny.Uni;

public interface SyncRuntimePoolContainerRefMethod {
    Uni<SyncRuntimePoolContainerRefResponse> execute(SyncRuntimePoolContainerRefRequest request);
}
