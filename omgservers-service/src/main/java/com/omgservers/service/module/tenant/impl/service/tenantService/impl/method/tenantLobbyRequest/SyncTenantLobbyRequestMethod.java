package com.omgservers.service.module.tenant.impl.service.tenantService.impl.method.tenantLobbyRequest;

import com.omgservers.schema.module.tenant.tenantLobbyRequest.SyncTenantLobbyRequestRequest;
import com.omgservers.schema.module.tenant.tenantLobbyRequest.SyncTenantLobbyRequestResponse;
import io.smallrye.mutiny.Uni;

public interface SyncTenantLobbyRequestMethod {
    Uni<SyncTenantLobbyRequestResponse> execute(SyncTenantLobbyRequestRequest request);
}
