package com.omgservers.service.shard.user.operation.testInterface;

import com.omgservers.schema.model.player.PlayerModel;
import com.omgservers.service.shard.user.impl.operation.userPlayer.UpsertPlayerOperation;
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
public class UpsertPlayerOperationTestInterface {
    private static final long TIMEOUT = 1L;

    final UpsertPlayerOperation upsertPlayerOperation;

    final PgPool pgPool;

    public ChangeContext<Boolean> upsertPlayer(final int slot,
                                               final PlayerModel player) {
        return Uni.createFrom().context(context -> {
                    final var changeContext = new ChangeContext<Boolean>(context);
                    return pgPool.withTransaction(sqlConnection -> upsertPlayerOperation
                                    .execute(changeContext, sqlConnection, slot, player))
                            .invoke(changeContext::setResult)
                            .replaceWith(changeContext);
                })
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }
}
