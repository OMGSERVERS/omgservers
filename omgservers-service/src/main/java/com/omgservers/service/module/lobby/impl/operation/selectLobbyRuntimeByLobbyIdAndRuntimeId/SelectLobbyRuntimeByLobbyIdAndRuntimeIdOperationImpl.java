package com.omgservers.service.module.lobby.impl.operation.selectLobbyRuntimeByLobbyIdAndRuntimeId;

import com.omgservers.model.lobbyRuntime.LobbyRuntimeModel;
import com.omgservers.service.module.lobby.impl.mappers.LobbyRuntimeModelMapper;
import com.omgservers.service.operation.selectObject.SelectObjectOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class SelectLobbyRuntimeByLobbyIdAndRuntimeIdOperationImpl implements SelectLobbyRuntimeByLobbyIdAndRuntimeIdOperation {

    final SelectObjectOperation selectObjectOperation;

    final LobbyRuntimeModelMapper lobbyRuntimeModelMapper;

    @Override
    public Uni<LobbyRuntimeModel> selectLobbyRuntimeByLobbyIdAndRuntimeId(final SqlConnection sqlConnection,
                                                                          final int shard,
                                                                          final Long lobbyId,
                                                                          final Long runtimeId) {
        return selectObjectOperation.selectObject(
                sqlConnection,
                shard,
                """
                        select runtimeId, lobby_id, created, modified, runtime_id, deleted
                        from $schema.tab_lobby_runtime
                        where lobby_id = $1 and runtime_id = $2
                        limit 1
                        """,
                List.of(lobbyId, runtimeId),
                "Lobby runtime",
                lobbyRuntimeModelMapper::fromRow);
    }
}
