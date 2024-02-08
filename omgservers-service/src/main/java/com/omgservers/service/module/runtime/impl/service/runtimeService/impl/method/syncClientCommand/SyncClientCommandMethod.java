package com.omgservers.service.module.runtime.impl.service.runtimeService.impl.method.syncClientCommand;

import com.omgservers.model.dto.runtime.SyncClientCommandRequest;
import com.omgservers.model.dto.runtime.SyncClientCommandResponse;
import io.smallrye.mutiny.Uni;

public interface SyncClientCommandMethod {
    Uni<SyncClientCommandResponse> syncClientCommand(SyncClientCommandRequest request);
}
