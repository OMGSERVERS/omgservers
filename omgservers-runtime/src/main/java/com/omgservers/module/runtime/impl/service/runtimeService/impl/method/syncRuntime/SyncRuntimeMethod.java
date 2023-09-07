package com.omgservers.module.runtime.impl.service.runtimeService.impl.method.syncRuntime;

import com.omgservers.dto.runtime.SyncRuntimeRequest;
import com.omgservers.dto.runtime.SyncRuntimeResponse;
import io.smallrye.mutiny.Uni;

import java.time.Duration;

public interface SyncRuntimeMethod {
    Uni<SyncRuntimeResponse> syncRuntime(SyncRuntimeRequest request);

    default SyncRuntimeResponse syncRuntime(long timeout, SyncRuntimeRequest request) {
        return syncRuntime(request)
                .await().atMost(Duration.ofSeconds(timeout));
    }
}
