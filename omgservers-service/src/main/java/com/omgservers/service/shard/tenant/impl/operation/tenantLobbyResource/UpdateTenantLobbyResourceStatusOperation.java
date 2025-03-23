package com.omgservers.service.shard.tenant.impl.operation.tenantLobbyResource;

import com.omgservers.schema.model.tenantLobbyResource.TenantLobbyResourceStatusEnum;
import com.omgservers.service.operation.server.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface UpdateTenantLobbyResourceStatusOperation {
    Uni<Boolean> execute(ChangeContext<?> changeContext,
                         SqlConnection sqlConnection,
                         int shard,
                         Long tenantId,
                         Long id,
                         TenantLobbyResourceStatusEnum status);
}
