package com.omgservers.service.shard.lobby.impl.operation.lobby;

import com.omgservers.schema.model.lobby.LobbyModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface SelectLobbyOperation {
    Uni<LobbyModel> execute(SqlConnection sqlConnection,
                            int slot,
                            Long id);
}
