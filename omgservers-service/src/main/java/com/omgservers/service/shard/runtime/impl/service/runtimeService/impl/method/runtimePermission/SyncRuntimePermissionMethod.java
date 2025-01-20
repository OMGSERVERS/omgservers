package com.omgservers.service.shard.runtime.impl.service.runtimeService.impl.method.runtimePermission;

import com.omgservers.schema.module.runtime.SyncRuntimePermissionRequest;
import com.omgservers.schema.module.runtime.SyncRuntimePermissionResponse;
import io.smallrye.mutiny.Uni;

public interface SyncRuntimePermissionMethod {
    Uni<SyncRuntimePermissionResponse> execute(SyncRuntimePermissionRequest request);
}
