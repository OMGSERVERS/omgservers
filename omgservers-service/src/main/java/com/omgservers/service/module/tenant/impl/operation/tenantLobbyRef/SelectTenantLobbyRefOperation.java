package com.omgservers.service.module.tenant.impl.operation.tenantLobbyRef;

import com.omgservers.schema.model.tenantLobbyRef.TenantLobbyRefModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface SelectTenantLobbyRefOperation {
    Uni<TenantLobbyRefModel> execute(SqlConnection sqlConnection,
                                     int shard,
                                     Long tenantId,
                                     Long id);
}
