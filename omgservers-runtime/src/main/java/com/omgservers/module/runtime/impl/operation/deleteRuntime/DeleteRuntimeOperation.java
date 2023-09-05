package com.omgservers.module.runtime.impl.operation.deleteRuntime;

import com.omgservers.operation.changeWithContext.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.time.Duration;

public interface DeleteRuntimeOperation {
    Uni<Boolean> deleteRuntime(ChangeContext<?> changeContext,
                               SqlConnection sqlConnection,
                               int shard,
                               Long id);

    default Boolean deleteRuntime(long timeout,
                                  PgPool pgPool,
                                  int shard,
                                  Long id) {
        return Uni.createFrom().context(context -> {
                    final var changeContext = new ChangeContext<Boolean>(context);
                    return pgPool.withTransaction(sqlConnection ->
                            deleteRuntime(changeContext, sqlConnection, shard, id));
                })
                .await().atMost(Duration.ofSeconds(timeout));
    }
}
