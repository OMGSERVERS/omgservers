package com.omgservers.application.module.runtimeModule.impl.service.runtimeInternalService.impl.method.syncRuntimeMethod;

import com.omgservers.application.module.runtimeModule.impl.service.runtimeInternalService.request.SyncRuntimeInternalRequest;
import io.smallrye.mutiny.Uni;

public interface SyncRuntimeMethod {
    Uni<Void> syncRuntime(SyncRuntimeInternalRequest request);
}
