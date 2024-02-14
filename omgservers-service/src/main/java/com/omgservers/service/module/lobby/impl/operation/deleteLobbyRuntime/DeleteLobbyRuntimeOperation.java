package com.omgservers.service.module.lobby.impl.operation.deleteLobbyRuntime;

import com.omgservers.service.operation.changeWithContext.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface DeleteLobbyRuntimeOperation {
    Uni<Boolean> deleteLobbyRuntime(ChangeContext<?> changeContext,
                                    SqlConnection sqlConnection,
                                    int shard,
                                    Long lobbyId,
                                    Long id);
}
