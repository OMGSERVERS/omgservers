package com.omgservers.service.shard.lobby.impl.operation.lobbyRuntimeRef.deleteLobbyRuntimeRef;

import com.omgservers.service.event.body.module.lobby.LobbyRuntimeRefDeletedEventBodyModel;
import com.omgservers.service.operation.server.ChangeContext;
import com.omgservers.service.operation.server.ChangeObjectOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;
import java.time.ZoneOffset;
import java.util.List;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class DeleteLobbyRuntimeRefOperationImpl implements DeleteLobbyRuntimeRefOperation {

    final ChangeObjectOperation changeObjectOperation;

    @Override
    public Uni<Boolean> deleteLobbyRuntimeRef(final ChangeContext<?> changeContext,
                                              final SqlConnection sqlConnection,
                                              final int shard,
                                              final Long lobbyId,
                                              final Long id) {
        return changeObjectOperation.changeObject(
                changeContext, sqlConnection, shard,
                """
                        update $schema.tab_lobby_runtime_ref
                        set modified = $3, deleted = true
                        where lobby_id = $1 and id = $2 and deleted = false
                        """,
                List.of(
                        lobbyId,
                        id,
                        Instant.now().atOffset(ZoneOffset.UTC)
                ),
                () -> new LobbyRuntimeRefDeletedEventBodyModel(lobbyId, id),
                () -> null
        );
    }
}
