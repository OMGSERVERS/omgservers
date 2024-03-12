package com.omgservers.service.module.lobby.operation.testInterface;

import com.omgservers.model.lobbyRuntimeRef.LobbyRuntimeRefModel;
import com.omgservers.service.module.lobby.impl.operation.upsertLobbyRuntimeRef.UpsertLobbyRuntimeRefOperation;
import com.omgservers.service.operation.changeWithContext.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class UpsertLobbyRuntimeRefOperationTestInterface {
    private static final long TIMEOUT = 1L;

    final UpsertLobbyRuntimeRefOperation upsertLobbyRuntimeRefOperation;

    final PgPool pgPool;

    public ChangeContext<Boolean> upsertLobbyRuntimeRef(final int shard,
                                                        final LobbyRuntimeRefModel lobbyRuntimeRef) {
        return Uni.createFrom().context(context -> {
                    final var changeContext = new ChangeContext<Boolean>(context);
                    return pgPool.withTransaction(sqlConnection -> upsertLobbyRuntimeRefOperation
                                    .upsertLobbyRuntimeRef(changeContext, sqlConnection, shard, lobbyRuntimeRef))
                            .invoke(changeContext::setResult)
                            .replaceWith(changeContext);
                })
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }
}
