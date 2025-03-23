package com.omgservers.service.shard.tenant.impl.service.tenantService.impl.method.tenantLobbyResource;

import com.omgservers.schema.module.tenant.tenantLobbyResource.UpdateTenantLobbyResourceStatusRequest;
import com.omgservers.schema.module.tenant.tenantLobbyResource.UpdateTenantLobbyResourceStatusResponse;
import io.smallrye.mutiny.Uni;

public interface UpdateTenantLobbyResourceStatusMethod {
    Uni<UpdateTenantLobbyResourceStatusResponse> execute(UpdateTenantLobbyResourceStatusRequest request);
}
