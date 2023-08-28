package com.omgservers.module.runtime.impl.service.runtimeShardedService.impl.method.syncRuntime;

import com.omgservers.dto.runtime.SyncRuntimeShardedRequest;
import com.omgservers.dto.runtime.SyncRuntimeShardedResponse;
import io.smallrye.mutiny.Uni;

import java.time.Duration;

public interface SyncRuntimeMethod {
    Uni<SyncRuntimeShardedResponse> syncRuntime(SyncRuntimeShardedRequest request);

    default SyncRuntimeShardedResponse syncRuntime(long timeout, SyncRuntimeShardedRequest request) {
        return syncRuntime(request)
                .await().atMost(Duration.ofSeconds(timeout));
    }
}
