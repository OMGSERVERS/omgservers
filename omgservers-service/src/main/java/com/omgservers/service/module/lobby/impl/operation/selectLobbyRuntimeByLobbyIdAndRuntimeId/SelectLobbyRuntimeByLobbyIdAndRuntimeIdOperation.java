package com.omgservers.service.module.lobby.impl.operation.selectLobbyRuntimeByLobbyIdAndRuntimeId;

import com.omgservers.model.lobbyRuntime.LobbyRuntimeModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface SelectLobbyRuntimeByLobbyIdAndRuntimeIdOperation {
    Uni<LobbyRuntimeModel> selectLobbyRuntimeByLobbyIdAndRuntimeId(SqlConnection sqlConnection,
                                                                   int shard,
                                                                   Long lobbyId,
                                                                   Long runtimeId);
}
