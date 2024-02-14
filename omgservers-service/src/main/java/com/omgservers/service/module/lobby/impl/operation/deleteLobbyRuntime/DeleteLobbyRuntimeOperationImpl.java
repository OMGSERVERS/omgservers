package com.omgservers.service.module.lobby.impl.operation.deleteLobbyRuntime;

import com.omgservers.model.event.body.LobbyRuntimeDeletedEventBodyModel;
import com.omgservers.service.operation.changeObject.ChangeObjectOperation;
import com.omgservers.service.operation.changeWithContext.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;
import java.time.ZoneOffset;
import java.util.Arrays;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class DeleteLobbyRuntimeOperationImpl implements DeleteLobbyRuntimeOperation {

    final ChangeObjectOperation changeObjectOperation;

    @Override
    public Uni<Boolean> deleteLobbyRuntime(final ChangeContext<?> changeContext,
                                           final SqlConnection sqlConnection,
                                           final int shard,
                                           final Long lobbyId,
                                           final Long id) {
        return changeObjectOperation.changeObject(
                changeContext, sqlConnection, shard,
                """
                        update $schema.tab_lobby_runtime
                        set modified = $3, deleted = true
                        where lobby_id = $1 and id = $2 and deleted = false
                        """,
                Arrays.asList(
                        lobbyId,
                        id,
                        Instant.now().atOffset(ZoneOffset.UTC)
                ),
                () -> new LobbyRuntimeDeletedEventBodyModel(lobbyId, id),
                () -> null
        );
    }
}
