package com.omgservers.application.module.runtimeModule.impl.service.runtimeInternalService.impl.method.syncRuntimeMethod;

import com.omgservers.application.module.runtimeModule.impl.service.runtimeInternalService.request.SyncRuntimeInternalRequest;
import com.omgservers.application.module.runtimeModule.impl.service.runtimeInternalService.response.SyncRuntimeInternalResponse;
import io.smallrye.mutiny.Uni;

public interface SyncRuntimeMethod {
    Uni<SyncRuntimeInternalResponse> syncRuntime(SyncRuntimeInternalRequest request);
}
