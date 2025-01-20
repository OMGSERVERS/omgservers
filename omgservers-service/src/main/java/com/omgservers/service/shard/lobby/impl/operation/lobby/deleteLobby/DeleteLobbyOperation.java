package com.omgservers.service.shard.lobby.impl.operation.lobby.deleteLobby;

import com.omgservers.service.operation.server.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface DeleteLobbyOperation {
    Uni<Boolean> deleteLobby(ChangeContext<?> changeContext,
                             SqlConnection sqlConnection,
                             int shard,
                             Long id);
}
