package com.omgservers.service.module.runtime.impl.service.runtimeService.impl.method.runtime.syncRuntime;

import com.omgservers.schema.module.runtime.SyncRuntimeRequest;
import com.omgservers.schema.module.runtime.SyncRuntimeResponse;
import io.smallrye.mutiny.Uni;

public interface SyncRuntimeMethod {
    Uni<SyncRuntimeResponse> syncRuntime(SyncRuntimeRequest request);
}
