package com.omgservers.service.shard.tenant.impl.service.tenantService.impl.method.tenantLobbyResource;

import com.omgservers.schema.module.tenant.tenantLobbyResource.DeleteTenantLobbyResourceRequest;
import com.omgservers.schema.module.tenant.tenantLobbyResource.DeleteTenantLobbyResourceResponse;
import io.smallrye.mutiny.Uni;

public interface DeleteTenantLobbyResourceMethod {
    Uni<DeleteTenantLobbyResourceResponse> execute(DeleteTenantLobbyResourceRequest request);
}
