package com.omgservers.service.shard.tenant.impl.operation.tenantLobbyRef;

import com.omgservers.schema.model.tenantLobbyRef.TenantLobbyRefModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.util.List;

public interface SelectActiveTenantLobbyRefsByTenantIdOperation {
    Uni<List<TenantLobbyRefModel>> execute(SqlConnection sqlConnection,
                                           int shard,
                                           Long tenantId);
}
