package com.omgservers.module.user.impl.operation.deleteClient;

import com.omgservers.operation.changeWithContext.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.time.Duration;

public interface DeleteClientOperation {
    Uni<Boolean> deleteClient(ChangeContext<?> changeContext,
                              SqlConnection sqlConnection,
                              int shard,
                              Long userId,
                              Long id);

    default Boolean deleteClient(long timeout, PgPool pgPool, int shard, Long userId, Long id) {
        return Uni.createFrom().context(context -> {
                    final var changeContext = new ChangeContext<Boolean>(context);
                    return pgPool.withTransaction(sqlConnection ->
                            deleteClient(changeContext, sqlConnection, shard, userId, id));
                })
                .await().atMost(Duration.ofSeconds(timeout));
    }
}
