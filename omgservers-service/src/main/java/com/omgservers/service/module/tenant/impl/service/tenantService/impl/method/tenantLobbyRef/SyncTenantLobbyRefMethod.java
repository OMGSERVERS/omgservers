package com.omgservers.service.module.tenant.impl.service.tenantService.impl.method.tenantLobbyRef;

import com.omgservers.schema.module.tenant.tenantLobbyRef.SyncTenantLobbyRefRequest;
import com.omgservers.schema.module.tenant.tenantLobbyRef.SyncTenantLobbyRefResponse;
import io.smallrye.mutiny.Uni;

public interface SyncTenantLobbyRefMethod {
    Uni<SyncTenantLobbyRefResponse> execute(SyncTenantLobbyRefRequest request);
}
