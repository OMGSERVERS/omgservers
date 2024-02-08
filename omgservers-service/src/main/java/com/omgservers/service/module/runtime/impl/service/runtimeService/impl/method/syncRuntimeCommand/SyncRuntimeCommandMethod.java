package com.omgservers.service.module.runtime.impl.service.runtimeService.impl.method.syncRuntimeCommand;

import com.omgservers.model.dto.runtime.SyncRuntimeCommandRequest;
import com.omgservers.model.dto.runtime.SyncRuntimeCommandResponse;
import io.smallrye.mutiny.Uni;

public interface SyncRuntimeCommandMethod {
    Uni<SyncRuntimeCommandResponse> syncRuntimeCommand(SyncRuntimeCommandRequest request);
}
