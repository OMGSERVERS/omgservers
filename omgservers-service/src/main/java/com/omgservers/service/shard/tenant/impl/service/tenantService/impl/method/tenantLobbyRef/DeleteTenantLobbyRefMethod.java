package com.omgservers.service.shard.tenant.impl.service.tenantService.impl.method.tenantLobbyRef;

import com.omgservers.schema.module.tenant.tenantLobbyRef.DeleteTenantLobbyRefRequest;
import com.omgservers.schema.module.tenant.tenantLobbyRef.DeleteTenantLobbyRefResponse;
import io.smallrye.mutiny.Uni;

public interface DeleteTenantLobbyRefMethod {
    Uni<DeleteTenantLobbyRefResponse> execute(DeleteTenantLobbyRefRequest request);
}
