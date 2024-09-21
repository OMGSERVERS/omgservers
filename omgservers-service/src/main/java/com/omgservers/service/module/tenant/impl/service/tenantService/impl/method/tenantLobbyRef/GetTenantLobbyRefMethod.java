package com.omgservers.service.module.tenant.impl.service.tenantService.impl.method.tenantLobbyRef;

import com.omgservers.schema.module.tenant.tenantLobbyRef.GetTenantLobbyRefRequest;
import com.omgservers.schema.module.tenant.tenantLobbyRef.GetTenantLobbyRefResponse;
import io.smallrye.mutiny.Uni;

public interface GetTenantLobbyRefMethod {
    Uni<GetTenantLobbyRefResponse> execute(GetTenantLobbyRefRequest request);
}
