package com.omgservers.service.shard.lobby.impl.operation.lobby.upsertLobby;

import com.omgservers.schema.model.lobby.LobbyModel;
import com.omgservers.service.operation.server.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface UpsertLobbyOperation {
    Uni<Boolean> upsertLobby(ChangeContext<?> changeContext,
                             SqlConnection sqlConnection,
                             int shard,
                             LobbyModel lobby);
}
