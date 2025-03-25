package com.omgservers.service.shard.runtime.impl.service.runtimeService.impl.method.runtime;

import com.omgservers.schema.module.runtime.runtime.SyncRuntimeRequest;
import com.omgservers.schema.module.runtime.runtime.SyncRuntimeResponse;
import io.smallrye.mutiny.Uni;

public interface SyncRuntimeMethod {
    Uni<SyncRuntimeResponse> execute(SyncRuntimeRequest request);
}
