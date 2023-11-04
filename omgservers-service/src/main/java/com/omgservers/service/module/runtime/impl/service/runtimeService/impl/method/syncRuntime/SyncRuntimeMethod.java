package com.omgservers.service.module.runtime.impl.service.runtimeService.impl.method.syncRuntime;

import com.omgservers.model.dto.runtime.SyncRuntimeRequest;
import com.omgservers.model.dto.runtime.SyncRuntimeResponse;
import io.smallrye.mutiny.Uni;

import java.time.Duration;

public interface SyncRuntimeMethod {
    Uni<SyncRuntimeResponse> syncRuntime(SyncRuntimeRequest request);

    default SyncRuntimeResponse syncRuntime(long timeout, SyncRuntimeRequest request) {
        return syncRuntime(request)
                .await().atMost(Duration.ofSeconds(timeout));
    }
}
