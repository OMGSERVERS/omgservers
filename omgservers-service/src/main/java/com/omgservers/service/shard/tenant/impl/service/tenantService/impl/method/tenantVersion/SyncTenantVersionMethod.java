package com.omgservers.service.shard.tenant.impl.service.tenantService.impl.method.tenantVersion;

import com.omgservers.schema.module.tenant.tenantVersion.SyncTenantVersionRequest;
import com.omgservers.schema.module.tenant.tenantVersion.SyncTenantVersionResponse;
import io.smallrye.mutiny.Uni;

public interface SyncTenantVersionMethod {
    Uni<SyncTenantVersionResponse> execute(SyncTenantVersionRequest request);
}
