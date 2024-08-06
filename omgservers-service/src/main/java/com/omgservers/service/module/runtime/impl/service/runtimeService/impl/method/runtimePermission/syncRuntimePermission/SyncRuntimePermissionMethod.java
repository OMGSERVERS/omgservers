package com.omgservers.service.module.runtime.impl.service.runtimeService.impl.method.runtimePermission.syncRuntimePermission;

import com.omgservers.schema.module.runtime.SyncRuntimePermissionRequest;
import com.omgservers.schema.module.runtime.SyncRuntimePermissionResponse;
import io.smallrye.mutiny.Uni;

public interface SyncRuntimePermissionMethod {
    Uni<SyncRuntimePermissionResponse> syncRuntimePermission(SyncRuntimePermissionRequest request);
}
