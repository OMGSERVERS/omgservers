package com.omgservers.service.module.tenant.impl.service.projectService.impl.method.syncProjectPermission;

import com.omgservers.model.dto.tenant.SyncProjectPermissionRequest;
import com.omgservers.model.dto.tenant.SyncProjectPermissionResponse;
import io.smallrye.mutiny.Uni;

public interface SyncProjectPermissionMethod {
    Uni<SyncProjectPermissionResponse> syncProjectPermission(SyncProjectPermissionRequest request);
}
