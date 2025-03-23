package com.omgservers.service.shard.tenant.impl.operation.tenantLobbyResource;

import com.omgservers.schema.model.tenantLobbyResource.TenantLobbyResourceModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface SelectTenantLobbyResourceOperation {
    Uni<TenantLobbyResourceModel> execute(SqlConnection sqlConnection,
                                          int shard,
                                          Long tenantId,
                                          Long id);
}
