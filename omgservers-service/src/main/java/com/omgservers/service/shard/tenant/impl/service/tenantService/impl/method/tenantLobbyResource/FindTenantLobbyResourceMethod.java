package com.omgservers.service.shard.tenant.impl.service.tenantService.impl.method.tenantLobbyResource;

import com.omgservers.schema.module.tenant.tenantLobbyResource.FindTenantLobbyResourceRequest;
import com.omgservers.schema.module.tenant.tenantLobbyResource.FindTenantLobbyResourceResponse;
import io.smallrye.mutiny.Uni;

public interface FindTenantLobbyResourceMethod {
    Uni<FindTenantLobbyResourceResponse> execute(FindTenantLobbyResourceRequest request);
}
