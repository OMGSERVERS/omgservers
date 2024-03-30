package com.omgservers.service.module.runtime.impl.service.runtimeService.impl.method.syncRuntimeServerContainerRef;

import com.omgservers.model.dto.runtime.serverRuntimeRef.SyncRuntimeServerContainerRefRequest;
import com.omgservers.model.dto.runtime.serverRuntimeRef.SyncRuntimeServerContainerRefResponse;
import io.smallrye.mutiny.Uni;

public interface SyncRuntimeServerContainerRefMethod {
    Uni<SyncRuntimeServerContainerRefResponse> syncRuntimeServerContainerRef(
            SyncRuntimeServerContainerRefRequest request);
}
