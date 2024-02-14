package com.omgservers.service.module.lobby.impl.operation.deleteLobby;

import com.omgservers.service.operation.changeWithContext.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface DeleteLobbyOperation {
    Uni<Boolean> deleteLobby(ChangeContext<?> changeContext,
                             SqlConnection sqlConnection,
                             int shard,
                             Long id);
}
