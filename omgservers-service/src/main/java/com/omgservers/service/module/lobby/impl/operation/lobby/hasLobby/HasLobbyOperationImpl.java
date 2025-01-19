package com.omgservers.service.module.lobby.impl.operation.lobby.hasLobby;

import com.omgservers.service.operation.server.HasObjectOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class HasLobbyOperationImpl implements HasLobbyOperation {

    final HasObjectOperation hasObjectOperation;

    @Override
    public Uni<Boolean> hasLobby(final SqlConnection sqlConnection,
                                 final int shard,
                                 final Long id) {
        return hasObjectOperation.hasObject(
                sqlConnection,
                shard,
                """
                        select id
                        from $schema.tab_lobby
                        where id = $1 and deleted = false
                        limit 1
                        """,
                List.of(id),
                "Lobby");
    }
}
