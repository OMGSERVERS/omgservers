package com.omgservers.service.shard.tenant.impl.service.tenantService.impl.method.tenantLobbyRequest;

import com.omgservers.schema.module.tenant.tenantLobbyRequest.ViewTenantLobbyRequestsRequest;
import com.omgservers.schema.module.tenant.tenantLobbyRequest.ViewTenantLobbyRequestsResponse;
import io.smallrye.mutiny.Uni;

public interface ViewTenantLobbyRequestsMethod {
    Uni<ViewTenantLobbyRequestsResponse> execute(ViewTenantLobbyRequestsRequest request);
}
