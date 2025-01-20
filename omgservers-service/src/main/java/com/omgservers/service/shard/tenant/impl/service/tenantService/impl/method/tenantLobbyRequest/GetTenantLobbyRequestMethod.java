package com.omgservers.service.shard.tenant.impl.service.tenantService.impl.method.tenantLobbyRequest;

import com.omgservers.schema.module.tenant.tenantLobbyRequest.GetTenantLobbyRequestRequest;
import com.omgservers.schema.module.tenant.tenantLobbyRequest.GetTenantLobbyRequestResponse;
import io.smallrye.mutiny.Uni;

public interface GetTenantLobbyRequestMethod {
    Uni<GetTenantLobbyRequestResponse> execute(GetTenantLobbyRequestRequest request);
}
