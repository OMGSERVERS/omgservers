package com.omgservers.service.shard.lobby.impl.operation.lobby;

import com.omgservers.schema.model.lobby.LobbyModel;
import com.omgservers.service.event.body.module.lobby.LobbyCreatedEventBodyModel;
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
class UpsertLobbyOperationImpl implements UpsertLobbyOperation {

    final ChangeObjectOperation changeObjectOperation;

    @Override
    public Uni<Boolean> execute(final ChangeContext<?> changeContext,
                                final SqlConnection sqlConnection,
                                final int shard,
                                final LobbyModel lobby) {
        return changeObjectOperation.changeObject(
                changeContext, sqlConnection, shard,
                """
                        insert into $schema.tab_lobby(
                            id, idempotency_key, created, modified, deployment_id, runtime_id, deleted)
                        values($1, $2, $3, $4, $5, $6, $7)
                        on conflict (id) do
                        nothing
                        """,
                List.of(
                        lobby.getId(),
                        lobby.getIdempotencyKey(),
                        lobby.getCreated().atOffset(ZoneOffset.UTC),
                        lobby.getModified().atOffset(ZoneOffset.UTC),
                        lobby.getDeploymentId(),
                        lobby.getRuntimeId(),
                        lobby.getDeleted()
                ),
                () -> new LobbyCreatedEventBodyModel(lobby.getId()),
                () -> null
        );
    }
}
