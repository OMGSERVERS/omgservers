package com.omgservers.service.module.tenant.impl.service.tenantService.impl.method.tenant.syncTenant;

import com.omgservers.schema.module.tenant.SyncTenantRequest;
import com.omgservers.schema.module.tenant.SyncTenantResponse;
import io.smallrye.mutiny.Uni;

public interface SyncTenantMethod {
    Uni<SyncTenantResponse> syncTenant(SyncTenantRequest request);
}
