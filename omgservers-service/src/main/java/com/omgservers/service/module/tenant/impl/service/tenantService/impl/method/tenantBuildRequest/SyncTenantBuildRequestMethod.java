package com.omgservers.service.module.tenant.impl.service.tenantService.impl.method.tenantBuildRequest;

import com.omgservers.schema.module.tenant.tenantBuildRequest.SyncTenantBuildRequestRequest;
import com.omgservers.schema.module.tenant.tenantBuildRequest.SyncTenantBuildRequestResponse;
import io.smallrye.mutiny.Uni;

public interface SyncTenantBuildRequestMethod {
    Uni<SyncTenantBuildRequestResponse> execute(SyncTenantBuildRequestRequest request);
}
