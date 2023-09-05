package com.omgservers.module.internal.impl.operation.deleteJob;

import com.omgservers.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.time.Duration;

public interface DeleteJobOperation {
    Uni<Boolean> deleteJob(ChangeContext<?> changeContext,
                           SqlConnection sqlConnection,
                           Long shardKey,
                           Long entity);

    default Boolean deleteJob(long timeout, PgPool pgPool, Long shardKey, Long entity) {
        return Uni.createFrom().context(context -> {
                    final var changeContext = new ChangeContext<Boolean>(context);
                    return pgPool.withTransaction(sqlConnection ->
                            deleteJob(changeContext, sqlConnection, shardKey, entity));
                })
                .await().atMost(Duration.ofSeconds(timeout));
    }
}
