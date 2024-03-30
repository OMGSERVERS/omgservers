package com.omgservers.service.module.lobby.impl.operation.lobbyRuntimeRef.selectLobbyRuntimeRefByLobbyId;

import com.omgservers.model.lobbyRuntimeRef.LobbyRuntimeRefModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface SelectLobbyRuntimeByLobbyIdOperation {
    Uni<LobbyRuntimeRefModel> selectLobbyRuntimeRefByLobbyId(SqlConnection sqlConnection,
                                                             int shard,
                                                             Long lobbyId);
}
