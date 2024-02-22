package com.omgservers.service.module.lobby.impl.operation.selectLobbyRuntimeRef;

import com.omgservers.model.lobbyRuntimeRef.LobbyRuntimeRefModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface SelectLobbyRuntimeRefOperation {
    Uni<LobbyRuntimeRefModel> selectLobbyRuntimeRef(SqlConnection sqlConnection,
                                                    int shard,
                                                    Long lobbyId,
                                                    Long id);
}
