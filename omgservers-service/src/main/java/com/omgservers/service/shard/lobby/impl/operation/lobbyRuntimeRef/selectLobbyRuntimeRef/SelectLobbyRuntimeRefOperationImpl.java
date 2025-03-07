package com.omgservers.service.shard.lobby.impl.operation.lobbyRuntimeRef.selectLobbyRuntimeRef;

import com.omgservers.schema.model.lobbyRuntimeRef.LobbyRuntimeRefModel;
import com.omgservers.service.shard.lobby.impl.mappers.LobbyRuntimeRefModelMapper;
import com.omgservers.service.operation.server.SelectObjectOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class SelectLobbyRuntimeRefOperationImpl implements SelectLobbyRuntimeRefOperation {

    final SelectObjectOperation selectObjectOperation;

    final LobbyRuntimeRefModelMapper lobbyRuntimeRefModelMapper;

    @Override
    public Uni<LobbyRuntimeRefModel> selectLobbyRuntimeRef(final SqlConnection sqlConnection,
                                                           final int shard,
                                                           final Long lobbyId,
                                                           final Long id) {
        return selectObjectOperation.selectObject(
                sqlConnection,
                shard,
                """
                        select id, idempotency_key, lobby_id, created, modified, runtime_id, deleted
                        from $schema.tab_lobby_runtime_ref
                        where id = $1
                        limit 1
                        """,
                List.of(id),
                "Lobby runtime ref",
                lobbyRuntimeRefModelMapper::fromRow);
    }
}
