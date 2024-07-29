package com.omgservers.service.module.lobby.impl.operation.lobby.upsertLobby;

import com.omgservers.schema.model.lobby.LobbyModel;
import com.omgservers.service.server.operation.changeWithContext.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface UpsertLobbyOperation {
    Uni<Boolean> upsertLobby(ChangeContext<?> changeContext,
                             SqlConnection sqlConnection,
                             int shard,
                             LobbyModel lobby);
}
