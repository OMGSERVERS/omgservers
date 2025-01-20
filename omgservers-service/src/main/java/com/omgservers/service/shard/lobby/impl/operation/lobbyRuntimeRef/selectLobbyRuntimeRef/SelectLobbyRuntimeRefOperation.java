package com.omgservers.service.shard.lobby.impl.operation.lobbyRuntimeRef.selectLobbyRuntimeRef;

import com.omgservers.schema.model.lobbyRuntimeRef.LobbyRuntimeRefModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface SelectLobbyRuntimeRefOperation {
    Uni<LobbyRuntimeRefModel> selectLobbyRuntimeRef(SqlConnection sqlConnection,
                                                    int shard,
                                                    Long lobbyId,
                                                    Long id);
}
