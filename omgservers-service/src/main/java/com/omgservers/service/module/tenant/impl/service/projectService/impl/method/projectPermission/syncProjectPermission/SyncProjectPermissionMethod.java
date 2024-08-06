package com.omgservers.service.module.tenant.impl.service.projectService.impl.method.projectPermission.syncProjectPermission;

import com.omgservers.schema.module.tenant.SyncProjectPermissionRequest;
import com.omgservers.schema.module.tenant.SyncProjectPermissionResponse;
import io.smallrye.mutiny.Uni;

public interface SyncProjectPermissionMethod {
    Uni<SyncProjectPermissionResponse> syncProjectPermission(SyncProjectPermissionRequest request);
}
