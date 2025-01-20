package com.omgservers.service.shard.lobby.impl.operation.lobby.hasLobby;

import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface HasLobbyOperation {
    Uni<Boolean> hasLobby(SqlConnection sqlConnection,
                          int shard,
                          Long id);
}
