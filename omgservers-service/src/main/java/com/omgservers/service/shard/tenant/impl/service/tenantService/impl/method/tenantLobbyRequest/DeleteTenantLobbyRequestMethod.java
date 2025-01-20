package com.omgservers.service.shard.tenant.impl.service.tenantService.impl.method.tenantLobbyRequest;

import com.omgservers.schema.module.tenant.tenantLobbyRequest.DeleteTenantLobbyRequestRequest;
import com.omgservers.schema.module.tenant.tenantLobbyRequest.DeleteTenantLobbyRequestResponse;
import io.smallrye.mutiny.Uni;

public interface DeleteTenantLobbyRequestMethod {
    Uni<DeleteTenantLobbyRequestResponse> execute(DeleteTenantLobbyRequestRequest request);
}
