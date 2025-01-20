package com.omgservers.service.shard.tenant.impl.service.tenantService.impl.method.tenantLobbyRequest;

import com.omgservers.schema.module.tenant.tenantLobbyRequest.FindTenantLobbyRequestRequest;
import com.omgservers.schema.module.tenant.tenantLobbyRequest.FindTenantLobbyRequestResponse;
import io.smallrye.mutiny.Uni;

public interface FindTenantLobbyRequestMethod {
    Uni<FindTenantLobbyRequestResponse> execute(FindTenantLobbyRequestRequest request);
}
