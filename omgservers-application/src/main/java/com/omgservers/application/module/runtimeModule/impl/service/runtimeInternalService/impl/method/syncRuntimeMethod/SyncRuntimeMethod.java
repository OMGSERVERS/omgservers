package com.omgservers.application.module.runtimeModule.impl.service.runtimeInternalService.impl.method.syncRuntimeMethod;

import com.omgservers.dto.runtimeModule.SyncRuntimeShardRequest;
import com.omgservers.dto.runtimeModule.SyncRuntimeInternalResponse;
import io.smallrye.mutiny.Uni;

public interface SyncRuntimeMethod {
    Uni<SyncRuntimeInternalResponse> syncRuntime(SyncRuntimeShardRequest request);
}
