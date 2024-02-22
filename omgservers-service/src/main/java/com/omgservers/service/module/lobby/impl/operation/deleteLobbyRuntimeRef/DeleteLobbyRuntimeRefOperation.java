package com.omgservers.service.module.lobby.impl.operation.deleteLobbyRuntimeRef;

import com.omgservers.service.operation.changeWithContext.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface DeleteLobbyRuntimeRefOperation {
    Uni<Boolean> deleteLobbyRuntimeRef(ChangeContext<?> changeContext,
                                       SqlConnection sqlConnection,
                                       int shard,
                                       Long lobbyId,
                                       Long id);
}
