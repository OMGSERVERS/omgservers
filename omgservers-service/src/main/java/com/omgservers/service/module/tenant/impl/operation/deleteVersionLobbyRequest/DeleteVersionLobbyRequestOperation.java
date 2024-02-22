package com.omgservers.service.module.tenant.impl.operation.deleteVersionLobbyRequest;

import com.omgservers.service.operation.changeWithContext.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface DeleteVersionLobbyRequestOperation {
    Uni<Boolean> deleteVersionLobbyRequest(ChangeContext<?> changeContext,
                                           SqlConnection sqlConnection,
                                           int shard,
                                           Long tenantId,
                                           Long id);
}
