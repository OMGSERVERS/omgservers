package com.omgservers.service.shard.tenant.impl.service.tenantService.impl.method.tenantMatchmakerResource;

import com.omgservers.schema.module.tenant.tenantMatchmakerResource.SyncTenantMatchmakerResourceRequest;
import com.omgservers.schema.module.tenant.tenantMatchmakerResource.SyncTenantMatchmakerResourceResponse;
import io.smallrye.mutiny.Uni;

public interface SyncTenantMatchmakerResourceMethod {
    Uni<SyncTenantMatchmakerResourceResponse> execute(SyncTenantMatchmakerResourceRequest request);
}
