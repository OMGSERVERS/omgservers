package com.omgservers.module.tenant.impl.service.tenantService.impl.method.syncTenant;

import com.omgservers.dto.tenant.SyncTenantRequest;
import com.omgservers.dto.tenant.SyncTenantResponse;
import io.smallrye.mutiny.Uni;

public interface SyncTenantMethod {
    Uni<SyncTenantResponse> syncTenant(SyncTenantRequest request);
}
