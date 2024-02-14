package com.omgservers.service.module.lobby.impl.operation.upsertLobbyRuntime;

import com.omgservers.model.event.body.LobbyRuntimeCreatedEventBodyModel;
import com.omgservers.model.lobbyRuntime.LobbyRuntimeModel;
import com.omgservers.service.operation.changeObject.ChangeObjectOperation;
import com.omgservers.service.operation.changeWithContext.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.ZoneOffset;
import java.util.Arrays;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class UpsertLobbyRuntimeOperationImpl implements UpsertLobbyRuntimeOperation {

    final ChangeObjectOperation changeObjectOperation;

    @Override
    public Uni<Boolean> upsertLobby(final ChangeContext<?> changeContext,
                                    final SqlConnection sqlConnection,
                                    final int shard,
                                    final LobbyRuntimeModel lobbyRuntime) {
        return changeObjectOperation.changeObject(
                changeContext, sqlConnection, shard,
                """
                        insert into $schema.tab_lobby_runtime(
                            id, lobby_id, created, modified, runtime_id, deleted)
                        values($1, $2, $3, $4, $5, $6)
                        on conflict (id) do
                        nothing
                        """,
                Arrays.asList(
                        lobbyRuntime.getId(),
                        lobbyRuntime.getLobbyId(),
                        lobbyRuntime.getCreated().atOffset(ZoneOffset.UTC),
                        lobbyRuntime.getModified().atOffset(ZoneOffset.UTC),
                        lobbyRuntime.getRuntimeId(),
                        lobbyRuntime.getDeleted()
                ),
                () -> new LobbyRuntimeCreatedEventBodyModel(lobbyRuntime.getLobbyId(), lobbyRuntime.getId()),
                () -> null
        );
    }
}
