package com.omgservers.service.shard.lobby.operation.testInterface;

import com.omgservers.schema.model.lobby.LobbyModel;
import com.omgservers.service.shard.lobby.impl.operation.lobby.upsertLobby.UpsertLobbyOperation;
import com.omgservers.service.operation.server.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class UpsertLobbyOperationTestInterface {
    private static final long TIMEOUT = 1L;

    final UpsertLobbyOperation upsertLobbyOperation;

    final PgPool pgPool;

    public ChangeContext<Boolean> upsertLobby(final int shard,
                                              final LobbyModel lobby) {
        return Uni.createFrom().context(context -> {
                    final var changeContext = new ChangeContext<Boolean>(context);
                    return pgPool.withTransaction(sqlConnection -> upsertLobbyOperation
                                    .upsertLobby(changeContext, sqlConnection, shard, lobby))
                            .invoke(changeContext::setResult)
                            .replaceWith(changeContext);
                })
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }
}
