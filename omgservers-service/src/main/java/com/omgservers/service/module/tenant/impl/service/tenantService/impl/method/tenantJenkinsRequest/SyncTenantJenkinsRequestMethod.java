package com.omgservers.service.module.tenant.impl.service.tenantService.impl.method.tenantJenkinsRequest;

import com.omgservers.schema.module.tenant.tenantJenkinsRequest.SyncTenantJenkinsRequestRequest;
import com.omgservers.schema.module.tenant.tenantJenkinsRequest.SyncTenantJenkinsRequestResponse;
import io.smallrye.mutiny.Uni;

public interface SyncTenantJenkinsRequestMethod {
    Uni<SyncTenantJenkinsRequestResponse> execute(SyncTenantJenkinsRequestRequest request);
}
