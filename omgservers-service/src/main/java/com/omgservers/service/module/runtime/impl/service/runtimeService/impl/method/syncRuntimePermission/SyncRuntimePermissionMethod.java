package com.omgservers.service.module.runtime.impl.service.runtimeService.impl.method.syncRuntimePermission;

import com.omgservers.model.dto.runtime.SyncRuntimePermissionRequest;
import com.omgservers.model.dto.runtime.SyncRuntimePermissionResponse;
import io.smallrye.mutiny.Uni;

public interface SyncRuntimePermissionMethod {
    Uni<SyncRuntimePermissionResponse> syncRuntimePermission(SyncRuntimePermissionRequest request);
}
