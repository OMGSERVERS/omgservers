package com.omgservers.service.shard.tenant.impl.service.tenantService.impl.method.tenantMatchmakerRef;

import com.omgservers.schema.module.tenant.tenantMatchmakerRef.SyncTenantMatchmakerRefRequest;
import com.omgservers.schema.module.tenant.tenantMatchmakerRef.SyncTenantMatchmakerRefResponse;
import io.smallrye.mutiny.Uni;

public interface SyncTenantMatchmakerRefMethod {
    Uni<SyncTenantMatchmakerRefResponse> execute(SyncTenantMatchmakerRefRequest request);
}
