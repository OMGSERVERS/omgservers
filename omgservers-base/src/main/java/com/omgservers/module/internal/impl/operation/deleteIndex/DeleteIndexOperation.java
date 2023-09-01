package com.omgservers.module.internal.impl.operation.deleteIndex;

import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.time.Duration;

public interface DeleteIndexOperation {
    Uni<Boolean> deleteIndex(SqlConnection sqlConnection, Long id);

    default Boolean deleteIndex(long timeout, PgPool pgPool, Long id) {
        return pgPool.withTransaction(sqlConnection -> deleteIndex(sqlConnection, id))
                .await().atMost(Duration.ofSeconds(timeout));
    }
}
