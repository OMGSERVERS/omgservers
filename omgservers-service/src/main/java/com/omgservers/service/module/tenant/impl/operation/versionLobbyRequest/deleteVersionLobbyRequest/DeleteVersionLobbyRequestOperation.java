package com.omgservers.service.module.tenant.impl.operation.versionLobbyRequest.deleteVersionLobbyRequest;

import com.omgservers.service.server.operation.changeWithContext.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface DeleteVersionLobbyRequestOperation {
    Uni<Boolean> deleteVersionLobbyRequest(ChangeContext<?> changeContext,
                                           SqlConnection sqlConnection,
                                           int shard,
                                           Long tenantId,
                                           Long id);
}
