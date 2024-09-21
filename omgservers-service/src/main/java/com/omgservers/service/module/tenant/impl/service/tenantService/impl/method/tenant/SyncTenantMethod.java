package com.omgservers.service.module.tenant.impl.service.tenantService.impl.method.tenant;

import com.omgservers.schema.module.tenant.tenant.SyncTenantRequest;
import com.omgservers.schema.module.tenant.tenant.SyncTenantResponse;
import io.smallrye.mutiny.Uni;

public interface SyncTenantMethod {
    Uni<SyncTenantResponse> syncTenant(SyncTenantRequest request);
}
