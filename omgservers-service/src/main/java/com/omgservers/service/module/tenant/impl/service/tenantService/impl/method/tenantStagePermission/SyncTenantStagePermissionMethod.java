package com.omgservers.service.module.tenant.impl.service.tenantService.impl.method.tenantStagePermission;

import com.omgservers.schema.module.tenant.tenantStagePermission.SyncTenantStagePermissionRequest;
import com.omgservers.schema.module.tenant.tenantStagePermission.SyncTenantStagePermissionResponse;
import io.smallrye.mutiny.Uni;

public interface SyncTenantStagePermissionMethod {
    Uni<SyncTenantStagePermissionResponse> execute(SyncTenantStagePermissionRequest request);
}
