package com.omgservers.service.module.tenant.impl.service.tenantService.impl.method.tenantMatchmakerRequest;

import com.omgservers.schema.module.tenant.tenantMatchmakerRequest.SyncTenantMatchmakerRequestRequest;
import com.omgservers.schema.module.tenant.tenantMatchmakerRequest.SyncTenantMatchmakerRequestResponse;
import io.smallrye.mutiny.Uni;

public interface SyncTenantMatchmakerRequestMethod {
    Uni<SyncTenantMatchmakerRequestResponse> execute(SyncTenantMatchmakerRequestRequest request);
}
