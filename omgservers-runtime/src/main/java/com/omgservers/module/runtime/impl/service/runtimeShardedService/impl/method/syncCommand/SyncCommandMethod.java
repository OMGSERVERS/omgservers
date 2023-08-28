package com.omgservers.module.runtime.impl.service.runtimeShardedService.impl.method.syncCommand;

import com.omgservers.dto.runtime.SyncRuntimeCommandShardedRequest;
import com.omgservers.dto.runtime.SyncRuntimeCommandShardedResponse;
import io.smallrye.mutiny.Uni;

public interface SyncCommandMethod {
    Uni<SyncRuntimeCommandShardedResponse> syncCommand(SyncRuntimeCommandShardedRequest request);
}
