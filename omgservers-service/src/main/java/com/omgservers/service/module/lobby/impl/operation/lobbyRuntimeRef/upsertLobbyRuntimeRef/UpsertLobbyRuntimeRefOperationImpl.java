package com.omgservers.service.module.lobby.impl.operation.lobbyRuntimeRef.upsertLobbyRuntimeRef;

import com.omgservers.schema.model.lobbyRuntimeRef.LobbyRuntimeRefModel;
import com.omgservers.service.event.body.module.lobby.LobbyRuntimeRefCreatedEventBodyModel;
import com.omgservers.service.operation.server.ChangeContext;
import com.omgservers.service.operation.server.ChangeObjectOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.ZoneOffset;
import java.util.List;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class UpsertLobbyRuntimeRefOperationImpl implements UpsertLobbyRuntimeRefOperation {

    final ChangeObjectOperation changeObjectOperation;

    @Override
    public Uni<Boolean> upsertLobbyRuntimeRef(final ChangeContext<?> changeContext,
                                              final SqlConnection sqlConnection,
                                              final int shard,
                                              final LobbyRuntimeRefModel lobbyRuntimeRef) {
        return changeObjectOperation.changeObject(
                changeContext, sqlConnection, shard,
                """
                        insert into $schema.tab_lobby_runtime_ref(
                            id, idempotency_key, lobby_id, created, modified, runtime_id, deleted)
                        values($1, $2, $3, $4, $5, $6, $7)
                        on conflict (id) do
                        nothing
                        """,
                List.of(
                        lobbyRuntimeRef.getId(),
                        lobbyRuntimeRef.getIdempotencyKey(),
                        lobbyRuntimeRef.getLobbyId(),
                        lobbyRuntimeRef.getCreated().atOffset(ZoneOffset.UTC),
                        lobbyRuntimeRef.getModified().atOffset(ZoneOffset.UTC),
                        lobbyRuntimeRef.getRuntimeId(),
                        lobbyRuntimeRef.getDeleted()
                ),
                () -> new LobbyRuntimeRefCreatedEventBodyModel(lobbyRuntimeRef.getLobbyId(), lobbyRuntimeRef.getId()),
                () -> null
        );
    }
}
