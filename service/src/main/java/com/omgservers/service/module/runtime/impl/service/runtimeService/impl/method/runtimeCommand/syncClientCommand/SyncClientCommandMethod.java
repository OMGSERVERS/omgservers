package com.omgservers.service.module.runtime.impl.service.runtimeService.impl.method.runtimeCommand.syncClientCommand;

import com.omgservers.schema.module.runtime.SyncClientCommandRequest;
import com.omgservers.schema.module.runtime.SyncClientCommandResponse;
import io.smallrye.mutiny.Uni;

public interface SyncClientCommandMethod {
    Uni<SyncClientCommandResponse> syncClientCommand(SyncClientCommandRequest request);
}
