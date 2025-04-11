package com.omgservers.service.shard.lobby.impl.operation.lobby;

import com.omgservers.schema.model.lobby.LobbyModel;
import com.omgservers.service.operation.server.SelectObjectOperation;
import com.omgservers.service.shard.lobby.impl.mappers.LobbyModelMapper;
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
    public Uni<LobbyModel> execute(final SqlConnection sqlConnection,
                                   final int shard,
                                   final Long id) {
        return selectObjectOperation.selectObject(
                sqlConnection,
                shard,
                """
                        select id, idempotency_key, created, modified, deployment_id, runtime_id, config, deleted
                        from $shard.tab_lobby
                        where id = $1
                        limit 1
                        """,
                List.of(id),
                "Lobby",
                lobbyModelMapper::fromRow);
    }
}
