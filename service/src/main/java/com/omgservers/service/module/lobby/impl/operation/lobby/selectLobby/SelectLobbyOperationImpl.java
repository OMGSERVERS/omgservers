package com.omgservers.service.module.lobby.impl.operation.lobby.selectLobby;

import com.omgservers.model.lobby.LobbyModel;
import com.omgservers.service.module.lobby.impl.mappers.LobbyModelMapper;
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
class SelectLobbyOperationImpl implements SelectLobbyOperation {

    final SelectObjectOperation selectObjectOperation;

    final LobbyModelMapper lobbyModelMapper;

    @Override
    public Uni<LobbyModel> selectLobby(final SqlConnection sqlConnection,
                                       final int shard,
                                       final Long id) {
        return selectObjectOperation.selectObject(
                sqlConnection,
                shard,
                """
                        select id, idempotency_key, created, modified, tenant_id, version_id, runtime_id, deleted
                        from $schema.tab_lobby
                        where id = $1
                        limit 1
                        """,
                List.of(id),
                "Lobby",
                lobbyModelMapper::fromRow);
    }
}
