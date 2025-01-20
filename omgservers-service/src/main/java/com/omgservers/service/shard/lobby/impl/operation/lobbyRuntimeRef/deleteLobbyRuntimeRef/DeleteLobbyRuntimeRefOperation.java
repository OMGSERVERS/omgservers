package com.omgservers.service.shard.lobby.impl.operation.lobbyRuntimeRef.deleteLobbyRuntimeRef;

import com.omgservers.service.operation.server.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface DeleteLobbyRuntimeRefOperation {
    Uni<Boolean> deleteLobbyRuntimeRef(ChangeContext<?> changeContext,
                                       SqlConnection sqlConnection,
                                       int shard,
                                       Long lobbyId,
                                       Long id);
}
