package com.omgservers.base.module.internal.impl.operation.deleteEvent;

import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.time.Duration;

public interface DeleteEventOperation {
    Uni<Boolean> deleteEvent(SqlConnection sqlConnection, Long id);

    default Boolean deleteEvent(long timeout, PgPool pgPool, Long id) {
        return pgPool.withTransaction(sqlConnection -> deleteEvent(sqlConnection, id))
                .await().atMost(Duration.ofSeconds(timeout));
    }
}
