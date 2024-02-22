package com.omgservers.service.module.runtime.impl.service.runtimeService.impl.method.syncRuntime;

import com.omgservers.model.dto.runtime.SyncRuntimeRequest;
import com.omgservers.model.dto.runtime.SyncRuntimeResponse;
import io.smallrye.mutiny.Uni;

public interface SyncRuntimeMethod {
    Uni<SyncRuntimeResponse> syncRuntime(SyncRuntimeRequest request);
}
