package com.omgservers.application.module.tenantModule.impl.service.tenantInternalService.impl.method.syncTenantMethod;

import com.omgservers.dto.tenantModule.SyncTenantInternalRequest;
import com.omgservers.dto.tenantModule.SyncTenantResponse;
import io.smallrye.mutiny.Uni;

public interface SyncTenantMethod {
    Uni<SyncTenantResponse> syncTenant(SyncTenantInternalRequest request);
}
