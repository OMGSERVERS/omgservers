package com.omgservers.module.user.impl.operation.deletePlayer;

import com.omgservers.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.time.Duration;

public interface DeletePlayerOperation {
    Uni<Boolean> deletePlayer(ChangeContext<?> changeContext,
                              SqlConnection sqlConnection,
                              int shard,
                              final Long userId,
                              Long id);

    default Boolean deletePlayer(long timeout, PgPool pgPool, int shard, Long userId, Long id) {
        return Uni.createFrom().context(context -> {
                    final var changeContext = new ChangeContext<Boolean>(context);
                    return pgPool.withTransaction(sqlConnection ->
                            deletePlayer(changeContext, sqlConnection, shard, userId, id));
                })
                .await().atMost(Duration.ofSeconds(timeout));
    }
}
