package com.omgservers.service.shard.runtime.impl.service.runtimeService.impl.method.runtimeCommand;

import com.omgservers.schema.module.runtime.SyncClientCommandRequest;
import com.omgservers.schema.module.runtime.SyncClientCommandResponse;
import io.smallrye.mutiny.Uni;

public interface SyncClientCommandMethod {
    Uni<SyncClientCommandResponse> execute(SyncClientCommandRequest request);
}
