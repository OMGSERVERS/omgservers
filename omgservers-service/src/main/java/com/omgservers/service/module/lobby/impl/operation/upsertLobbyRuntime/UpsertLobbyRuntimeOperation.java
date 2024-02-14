package com.omgservers.service.module.lobby.impl.operation.upsertLobbyRuntime;

import com.omgservers.model.lobbyRuntime.LobbyRuntimeModel;
import com.omgservers.service.operation.changeWithContext.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface UpsertLobbyRuntimeOperation {
    Uni<Boolean> upsertLobby(ChangeContext<?> changeContext,
                             SqlConnection sqlConnection,
                             int shard,
                             LobbyRuntimeModel lobbyRuntime);
}
