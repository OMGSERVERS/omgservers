package com.omgservers.module.tenant.impl.service.tenantShardedService.impl.method.syncTenantPermission;

import com.omgservers.dto.tenant.SyncTenantPermissionShardedRequest;
import com.omgservers.dto.tenant.SyncTenantPermissionShardedResponse;
import io.smallrye.mutiny.Uni;

public interface SyncTenantPermissionMethod {
    Uni<SyncTenantPermissionShardedResponse> syncTenantPermission(SyncTenantPermissionShardedRequest request);
}
