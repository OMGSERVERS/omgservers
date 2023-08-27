package com.omgservers.module.runtime.impl.service.runtimeShardedService.impl.method.syncCommand;

import com.omgservers.dto.runtime.SyncCommandShardedRequest;
import com.omgservers.dto.runtime.SyncCommandInternalResponse;
import io.smallrye.mutiny.Uni;

public interface SyncCommandMethod {
    Uni<SyncCommandInternalResponse> syncCommand(SyncCommandShardedRequest request);
}
