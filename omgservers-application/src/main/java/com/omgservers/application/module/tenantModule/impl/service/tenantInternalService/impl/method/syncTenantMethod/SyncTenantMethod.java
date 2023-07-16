package com.omgservers.application.module.tenantModule.impl.service.tenantInternalService.impl.method.syncTenantMethod;

import com.omgservers.application.module.tenantModule.impl.service.tenantInternalService.request.SyncTenantInternalRequest;
import com.omgservers.application.module.tenantModule.impl.service.tenantInternalService.response.SyncTenantResponse;
import io.smallrye.mutiny.Uni;

public interface SyncTenantMethod {
    Uni<SyncTenantResponse> syncTenant(SyncTenantInternalRequest request);
}
