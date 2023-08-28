package com.omgservers.module.tenant.impl.service.tenantShardedService.impl.method.syncTenant;

import com.omgservers.dto.tenant.SyncTenantShardedRequest;
import com.omgservers.dto.tenant.SyncTenantShardedResponse;
import io.smallrye.mutiny.Uni;

public interface SyncTenantMethod {
    Uni<SyncTenantShardedResponse> syncTenant(SyncTenantShardedRequest request);
}
