package com.omgservers.service.shard.lobby.impl.operation.lobbyRuntimeRef.upsertLobbyRuntimeRef;

import com.omgservers.schema.model.lobbyRuntimeRef.LobbyRuntimeRefModel;
import com.omgservers.service.operation.server.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface UpsertLobbyRuntimeRefOperation {
    Uni<Boolean> upsertLobbyRuntimeRef(ChangeContext<?> changeContext,
                                       SqlConnection sqlConnection,
                                       int shard,
                                       LobbyRuntimeRefModel lobbyRuntimeRef);
}
