package com.omgservers.module.tenant.impl.service.tenantShardedService.impl.method.syncTenantPermission;

import com.omgservers.dto.tenantModule.SyncTenantPermissionShardRequest;
import com.omgservers.dto.tenantModule.SyncTenantPermissionResponse;
import io.smallrye.mutiny.Uni;

public interface SyncTenantPermissionMethod {
    Uni<SyncTenantPermissionResponse> syncTenantPermission(SyncTenantPermissionShardRequest request);
}
