package com.omgservers.service.shard.runtime.impl.service.runtimeService.impl.method.runtimeCommand;

import com.omgservers.schema.module.runtime.runtimeCommand.SyncRuntimeCommandRequest;
import com.omgservers.schema.module.runtime.runtimeCommand.SyncRuntimeCommandResponse;
import io.smallrye.mutiny.Uni;

public interface SyncRuntimeCommandMethod {
    Uni<SyncRuntimeCommandResponse> execute(SyncRuntimeCommandRequest request);
}
