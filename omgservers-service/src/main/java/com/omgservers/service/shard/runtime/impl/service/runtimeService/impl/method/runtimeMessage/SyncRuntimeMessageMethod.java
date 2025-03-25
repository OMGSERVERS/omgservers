package com.omgservers.service.shard.runtime.impl.service.runtimeService.impl.method.runtimeMessage;

import com.omgservers.schema.module.runtime.runtimeMessage.SyncRuntimeMessageRequest;
import com.omgservers.schema.module.runtime.runtimeMessage.SyncRuntimeMessageResponse;
import io.smallrye.mutiny.Uni;

public interface SyncRuntimeMessageMethod {
    Uni<SyncRuntimeMessageResponse> execute(SyncRuntimeMessageRequest request);
}
