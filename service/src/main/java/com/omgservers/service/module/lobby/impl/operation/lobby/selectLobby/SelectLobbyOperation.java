package com.omgservers.service.module.lobby.impl.operation.lobby.selectLobby;

import com.omgservers.model.lobby.LobbyModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface SelectLobbyOperation {
    Uni<LobbyModel> selectLobby(SqlConnection sqlConnection,
                                int shard,
                                Long id);
}
