package com.omgservers.service.module.lobby.impl.operation.upsertLobby;

import com.omgservers.model.event.body.LobbyCreatedEventBodyModel;
import com.omgservers.model.lobby.LobbyModel;
import com.omgservers.service.operation.changeObject.ChangeObjectOperation;
import com.omgservers.service.operation.changeWithContext.ChangeContext;
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
class UpsertLobbyOperationImpl implements UpsertLobbyOperation {

    final ChangeObjectOperation changeObjectOperation;

    @Override
    public Uni<Boolean> upsertLobby(final ChangeContext<?> changeContext,
                                    final SqlConnection sqlConnection,
                                    final int shard,
                                    final LobbyModel lobby) {
        return changeObjectOperation.changeObject(
                changeContext, sqlConnection, shard,
                """
                        insert into $schema.tab_lobby(
                            id, idempotency_key, created, modified, tenant_id, version_id, runtime_id, deleted)
                        values($1, $2, $3, $4, $5, $6, $7, $8)
                        on conflict (id) do
                        nothing
                        """,
                List.of(
                        lobby.getId(),
                        lobby.getIdempotencyKey(),
                        lobby.getCreated().atOffset(ZoneOffset.UTC),
                        lobby.getModified().atOffset(ZoneOffset.UTC),
                        lobby.getTenantId(),
                        lobby.getVersionId(),
                        lobby.getRuntimeId(),
                        lobby.getDeleted()
                ),
                () -> new LobbyCreatedEventBodyModel(lobby.getId()),
                () -> null
        );
    }
}
