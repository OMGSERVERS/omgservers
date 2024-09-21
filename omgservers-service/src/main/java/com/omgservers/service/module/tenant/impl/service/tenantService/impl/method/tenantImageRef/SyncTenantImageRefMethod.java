package com.omgservers.service.module.tenant.impl.service.tenantService.impl.method.tenantImageRef;

import com.omgservers.schema.module.tenant.tenantImageRef.SyncTenantImageRefRequest;
import com.omgservers.schema.module.tenant.tenantImageRef.SyncTenantImageRefResponse;
import io.smallrye.mutiny.Uni;

public interface SyncTenantImageRefMethod {
    Uni<SyncTenantImageRefResponse> execute(SyncTenantImageRefRequest request);
}
