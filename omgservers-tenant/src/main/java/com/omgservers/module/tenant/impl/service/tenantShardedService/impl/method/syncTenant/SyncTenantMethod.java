package com.omgservers.module.tenant.impl.service.tenantShardedService.impl.method.syncTenant;

import com.omgservers.dto.tenantModule.SyncTenantShardRequest;
import com.omgservers.dto.tenantModule.SyncTenantResponse;
import io.smallrye.mutiny.Uni;

public interface SyncTenantMethod {
    Uni<SyncTenantResponse> syncTenant(SyncTenantShardRequest request);
}
