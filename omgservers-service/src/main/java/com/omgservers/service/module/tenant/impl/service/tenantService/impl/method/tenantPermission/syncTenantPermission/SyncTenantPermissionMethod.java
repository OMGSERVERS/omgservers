package com.omgservers.service.module.tenant.impl.service.tenantService.impl.method.tenantPermission.syncTenantPermission;

import com.omgservers.schema.module.tenant.SyncTenantPermissionRequest;
import com.omgservers.schema.module.tenant.SyncTenantPermissionResponse;
import io.smallrye.mutiny.Uni;

public interface SyncTenantPermissionMethod {
    Uni<SyncTenantPermissionResponse> syncTenantPermission(SyncTenantPermissionRequest request);
}
