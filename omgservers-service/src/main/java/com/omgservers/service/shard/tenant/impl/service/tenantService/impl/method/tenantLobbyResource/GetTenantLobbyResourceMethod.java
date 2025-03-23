package com.omgservers.service.shard.tenant.impl.service.tenantService.impl.method.tenantLobbyResource;

import com.omgservers.schema.module.tenant.tenantLobbyResource.GetTenantLobbyResourceRequest;
import com.omgservers.schema.module.tenant.tenantLobbyResource.GetTenantLobbyResourceResponse;
import io.smallrye.mutiny.Uni;

public interface GetTenantLobbyResourceMethod {
    Uni<GetTenantLobbyResourceResponse> execute(GetTenantLobbyResourceRequest request);
}
