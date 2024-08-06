package com.omgservers.service.module.lobby.impl.operation.lobbyRuntimeRef.deleteLobbyRuntimeRef;

import com.omgservers.service.server.operation.changeWithContext.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface DeleteLobbyRuntimeRefOperation {
    Uni<Boolean> deleteLobbyRuntimeRef(ChangeContext<?> changeContext,
                                       SqlConnection sqlConnection,
                                       int shard,
                                       Long lobbyId,
                                       Long id);
}
