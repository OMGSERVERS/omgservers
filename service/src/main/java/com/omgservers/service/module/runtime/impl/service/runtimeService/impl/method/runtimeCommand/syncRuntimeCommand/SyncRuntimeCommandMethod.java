package com.omgservers.service.module.runtime.impl.service.runtimeService.impl.method.runtimeCommand.syncRuntimeCommand;

import com.omgservers.schema.module.runtime.SyncRuntimeCommandRequest;
import com.omgservers.schema.module.runtime.SyncRuntimeCommandResponse;
import io.smallrye.mutiny.Uni;

public interface SyncRuntimeCommandMethod {
    Uni<SyncRuntimeCommandResponse> syncRuntimeCommand(SyncRuntimeCommandRequest request);
}
