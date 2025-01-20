package com.omgservers.service.shard.tenant.impl.operation.tenantLobbyRequest;

import com.omgservers.schema.model.tenantLobbyRequest.TenantLobbyRequestModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface SelectTenantLobbyRequestByLobbyIdOperation {
    Uni<TenantLobbyRequestModel> execute(SqlConnection sqlConnection,
                                         int shard,
                                         Long tenantId,
                                         Long tenantDeploymentId,
                                         Long lobbyId);
}
