package com.omgservers.service.module.runtime.impl.service.runtimeService.impl.method.syncRuntimeClient;

import com.omgservers.model.dto.runtime.SyncRuntimeClientRequest;
import com.omgservers.model.dto.runtime.SyncRuntimeClientResponse;
import io.smallrye.mutiny.Uni;

public interface SyncRuntimeClientMethod {
    Uni<SyncRuntimeClientResponse> syncRuntimeClient(SyncRuntimeClientRequest request);
}
