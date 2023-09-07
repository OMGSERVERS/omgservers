package com.omgservers.module.tenant.impl.service.tenantService.impl.method.syncTenantPermission;

import com.omgservers.dto.tenant.SyncTenantPermissionRequest;
import com.omgservers.dto.tenant.SyncTenantPermissionResponse;
import io.smallrye.mutiny.Uni;

public interface SyncTenantPermissionMethod {
    Uni<SyncTenantPermissionResponse> syncTenantPermission(SyncTenantPermissionRequest request);
}
