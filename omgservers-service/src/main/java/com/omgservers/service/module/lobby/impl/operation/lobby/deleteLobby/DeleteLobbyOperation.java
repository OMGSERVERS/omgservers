package com.omgservers.service.module.lobby.impl.operation.lobby.deleteLobby;

import com.omgservers.service.server.operation.changeWithContext.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface DeleteLobbyOperation {
    Uni<Boolean> deleteLobby(ChangeContext<?> changeContext,
                             SqlConnection sqlConnection,
                             int shard,
                             Long id);
}
