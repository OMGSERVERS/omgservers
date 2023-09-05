package com.omgservers.module.user.impl.operation.deleteObject;

import com.omgservers.operation.changeWithContext.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.time.Duration;

public interface DeleteObjectOperation {
    Uni<Boolean> deleteObject(ChangeContext<?> changeContext,
                              SqlConnection sqlConnection,
                              int shard,
                              Long userId,
                              Long playerId,
                              Long id);

    default Boolean deleteObject(long timeout, PgPool pgPool, int shard, Long userId, Long playerId, Long id) {
        return Uni.createFrom().context(context -> {
                    final var changeContext = new ChangeContext<Boolean>(context);
                    return pgPool.withTransaction(sqlConnection ->
                            deleteObject(changeContext, sqlConnection, shard, userId, playerId, id));
                })
                .await().atMost(Duration.ofSeconds(timeout));
    }
}
