package com.omgservers.module.tenant.impl.service.projectService.impl.method.syncProjectPermission;

import com.omgservers.dto.tenant.SyncProjectPermissionRequest;
import com.omgservers.dto.tenant.SyncProjectPermissionResponse;
import io.smallrye.mutiny.Uni;

public interface SyncProjectPermissionMethod {
    Uni<SyncProjectPermissionResponse> syncProjectPermission(SyncProjectPermissionRequest request);
}
