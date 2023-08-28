package com.omgservers.module.runtime.impl.service.runtimeShardedService.impl.method.syncRuntimeCommand;

import com.omgservers.dto.runtime.SyncRuntimeCommandShardedRequest;
import com.omgservers.dto.runtime.SyncRuntimeCommandShardedResponse;
import io.smallrye.mutiny.Uni;

import java.time.Duration;

public interface SyncRuntimeCommandMethod {
    Uni<SyncRuntimeCommandShardedResponse> syncRuntimeCommand(SyncRuntimeCommandShardedRequest request);

    default SyncRuntimeCommandShardedResponse syncRuntimeCommand(long timeout,
                                                                 SyncRuntimeCommandShardedRequest request) {
        return syncRuntimeCommand(request)
                .await().atMost(Duration.ofSeconds(timeout));
    }
}
