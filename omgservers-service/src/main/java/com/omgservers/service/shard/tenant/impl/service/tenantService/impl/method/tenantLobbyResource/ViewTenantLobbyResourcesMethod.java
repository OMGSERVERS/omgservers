package com.omgservers.service.shard.tenant.impl.service.tenantService.impl.method.tenantLobbyResource;

import com.omgservers.schema.module.tenant.tenantLobbyResource.ViewTenantLobbyResourcesRequest;
import com.omgservers.schema.module.tenant.tenantLobbyResource.ViewTenantLobbyResourcesResponse;
import io.smallrye.mutiny.Uni;

public interface ViewTenantLobbyResourcesMethod {
    Uni<ViewTenantLobbyResourcesResponse> execute(ViewTenantLobbyResourcesRequest request);
}
