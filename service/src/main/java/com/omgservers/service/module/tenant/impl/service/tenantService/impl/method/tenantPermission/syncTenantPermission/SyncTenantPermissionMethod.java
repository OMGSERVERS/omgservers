package com.omgservers.service.module.tenant.impl.service.tenantService.impl.method.tenantPermission.syncTenantPermission;

import com.omgservers.model.dto.tenant.SyncTenantPermissionRequest;
import com.omgservers.model.dto.tenant.SyncTenantPermissionResponse;
import io.smallrye.mutiny.Uni;

public interface SyncTenantPermissionMethod {
    Uni<SyncTenantPermissionResponse> syncTenantPermission(SyncTenantPermissionRequest request);
}
