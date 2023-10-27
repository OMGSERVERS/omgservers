package com.omgservers.module.runtime.impl.service.runtimeService.impl.method.syncRuntimeCommand;

import com.omgservers.dto.runtime.SyncRuntimeCommandRequest;
import com.omgservers.dto.runtime.SyncRuntimeCommandResponse;
import io.smallrye.mutiny.Uni;

import java.time.Duration;

public interface SyncRuntimeCommandMethod {
    Uni<SyncRuntimeCommandResponse> syncRuntimeCommand(SyncRuntimeCommandRequest request);

    default SyncRuntimeCommandResponse syncRuntimeCommand(long timeout,
                                                          SyncRuntimeCommandRequest request) {
        return syncRuntimeCommand(request)
                .await().atMost(Duration.ofSeconds(timeout));
    }
}
