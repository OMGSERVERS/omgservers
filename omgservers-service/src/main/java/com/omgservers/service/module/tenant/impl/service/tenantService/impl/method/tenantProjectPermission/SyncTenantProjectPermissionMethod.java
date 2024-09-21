package com.omgservers.service.module.tenant.impl.service.tenantService.impl.method.tenantProjectPermission;

import com.omgservers.schema.module.tenant.tenantProjectPermission.SyncTenantProjectPermissionRequest;
import com.omgservers.schema.module.tenant.tenantProjectPermission.SyncTenantProjectPermissionResponse;
import io.smallrye.mutiny.Uni;

public interface SyncTenantProjectPermissionMethod {
    Uni<SyncTenantProjectPermissionResponse> execute(SyncTenantProjectPermissionRequest request);
}
