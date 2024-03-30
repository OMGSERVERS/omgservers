package com.omgservers.service.module.user.operation.testInterface;

import com.omgservers.model.player.PlayerModel;
import com.omgservers.service.module.user.impl.operation.userPlayer.selectPlayer.SelectPlayerOperation;
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
                        .selectPlayer(sqlConnection, shard, userId, id))
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }
}
