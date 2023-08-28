package com.omgservers.module.runtime.impl.service.runtimeShardedService.impl.method.syncRuntimeCommand;

import com.omgservers.dto.runtime.SyncRuntimeCommandShardedRequest;
import com.omgservers.dto.runtime.SyncRuntimeCommandShardedResponse;
import io.smallrye.mutiny.Uni;

public interface SyncRuntimeCommandMethod {
    Uni<SyncRuntimeCommandShardedResponse> syncRuntimeCommand(SyncRuntimeCommandShardedRequest request);
}
