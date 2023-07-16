package com.omgservers.application.module.internalModule.impl.operation.deleteEventOperation;

import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.time.Duration;
import java.util.UUID;

public interface DeleteEventOperation {
    Uni<Boolean> deleteEvent(SqlConnection sqlConnection, UUID uuid);

    default Boolean deleteEvent(long timeout, PgPool pgPool, UUID uuid) {
        return pgPool.withTransaction(sqlConnection -> deleteEvent(sqlConnection, uuid))
                .await().atMost(Duration.ofSeconds(timeout));
    }
}
