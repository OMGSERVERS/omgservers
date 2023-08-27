package com.omgservers.module.tenant.impl.service.tenantShardedService.impl.method.syncTenant;

import com.omgservers.dto.tenant.SyncTenantShardedRequest;
import com.omgservers.dto.tenant.SyncTenantResponse;
import io.smallrye.mutiny.Uni;

public interface SyncTenantMethod {
    Uni<SyncTenantResponse> syncTenant(SyncTenantShardedRequest request);
}
