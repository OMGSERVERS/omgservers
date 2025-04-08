package com.omgservers.service.shard.user.operation.testInterface;

import com.omgservers.schema.model.player.PlayerModel;
import com.omgservers.service.shard.user.impl.operation.userPlayer.SelectPlayerOperation;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class SelectPlayerOperationTestInterface {
    private static final long TIMEOUT = 1L;

    final SelectPlayerOperation selectPlayerOperation;

    final PgPool pgPool;

    public PlayerModel selectPlayer(final int shard,
                                    final Long userId,
                                    final Long id,
                                    final Boolean deleted) {
        return pgPool.withTransaction(sqlConnection -> selectPlayerOperation
                        .execute(sqlConnection, shard, userId, id))
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }
}
