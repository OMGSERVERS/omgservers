package com.omgservers.service.module.tenant.impl.service.tenantService.impl.method.tenantLobbyRef;

import com.omgservers.schema.module.tenant.tenantLobbyRef.ViewTenantLobbyRefsRequest;
import com.omgservers.schema.module.tenant.tenantLobbyRef.ViewTenantLobbyRefsResponse;
import io.smallrye.mutiny.Uni;

public interface ViewTenantLobbyRefsMethod {
    Uni<ViewTenantLobbyRefsResponse> execute(ViewTenantLobbyRefsRequest request);
}
