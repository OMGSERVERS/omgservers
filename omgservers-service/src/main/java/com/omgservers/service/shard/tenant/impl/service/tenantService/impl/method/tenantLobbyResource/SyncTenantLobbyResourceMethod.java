package com.omgservers.service.shard.tenant.impl.service.tenantService.impl.method.tenantLobbyResource;

import com.omgservers.schema.module.tenant.tenantLobbyResource.SyncTenantLobbyResourceRequest;
import com.omgservers.schema.module.tenant.tenantLobbyResource.SyncTenantLobbyResourceResponse;
import io.smallrye.mutiny.Uni;

public interface SyncTenantLobbyResourceMethod {
    Uni<SyncTenantLobbyResourceResponse> execute(SyncTenantLobbyResourceRequest request);
}
