package com.omgservers.service.module.lobby.impl.operation.lobbyRuntimeRef.upsertLobbyRuntimeRef;

import com.omgservers.schema.model.lobbyRuntimeRef.LobbyRuntimeRefModel;
import com.omgservers.service.server.operation.changeWithContext.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface UpsertLobbyRuntimeRefOperation {
    Uni<Boolean> upsertLobbyRuntimeRef(ChangeContext<?> changeContext,
                                       SqlConnection sqlConnection,
                                       int shard,
                                       LobbyRuntimeRefModel lobbyRuntimeRef);
}
