package com.omgservers.module.runtime.impl.service.runtimeService.impl.method.syncRuntimeGrant;

import com.omgservers.dto.runtime.SyncRuntimeGrantRequest;
import com.omgservers.dto.runtime.SyncRuntimeGrantResponse;
import io.smallrye.mutiny.Uni;

import java.time.Duration;

public interface SyncRuntimeGrantMethod {
    Uni<SyncRuntimeGrantResponse> syncRuntimeGrant(SyncRuntimeGrantRequest request);

    default SyncRuntimeGrantResponse syncRuntimeGrant(long timeout,
                                                      SyncRuntimeGrantRequest request) {
        return syncRuntimeGrant(request)
                .await().atMost(Duration.ofSeconds(timeout));
    }
}
