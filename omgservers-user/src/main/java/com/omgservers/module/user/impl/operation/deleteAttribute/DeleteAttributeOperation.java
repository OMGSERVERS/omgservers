package com.omgservers.module.user.impl.operation.deleteAttribute;

import com.omgservers.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.time.Duration;

public interface DeleteAttributeOperation {
    Uni<Boolean> deleteAttribute(ChangeContext<?> changeContext,
                                 SqlConnection sqlConnection,
                                 int shard,
                                 Long userId,
                                 Long playerId,
                                 String name);

    default Boolean deleteAttribute(long timeout, PgPool pgPool, int shard, Long userId, Long playerId, String name) {
        return Uni.createFrom().context(context -> {
                    final var changeContext = new ChangeContext<Boolean>(context);
                    return pgPool.withTransaction(sqlConnection ->
                            deleteAttribute(changeContext, sqlConnection, shard, userId, playerId, name));
                })
                .await().atMost(Duration.ofSeconds(timeout));
    }
}
