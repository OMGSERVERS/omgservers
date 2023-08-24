package com.omgservers.application.module.runtimeModule.impl.operation.deleteCommandOperation;

import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.time.Duration;

public interface DeleteCommandOperation {
    Uni<Boolean> deleteCommand(SqlConnection sqlConnection, int shard, Long id);

    default Boolean deleteCommand(long timeout, PgPool pgPool, int shard, Long id) {
        return pgPool.withTransaction(sqlConnection -> deleteCommand(sqlConnection, shard, id))
                .await().atMost(Duration.ofSeconds(timeout));
    }
}
