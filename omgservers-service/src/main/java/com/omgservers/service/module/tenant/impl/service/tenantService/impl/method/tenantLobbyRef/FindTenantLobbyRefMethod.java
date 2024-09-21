package com.omgservers.service.module.tenant.impl.service.tenantService.impl.method.tenantLobbyRef;

import com.omgservers.schema.module.tenant.tenantLobbyRef.FindTenantLobbyRefRequest;
import com.omgservers.schema.module.tenant.tenantLobbyRef.FindTenantLobbyRefResponse;
import io.smallrye.mutiny.Uni;

public interface FindTenantLobbyRefMethod {
    Uni<FindTenantLobbyRefResponse> execute(FindTenantLobbyRefRequest request);
}
