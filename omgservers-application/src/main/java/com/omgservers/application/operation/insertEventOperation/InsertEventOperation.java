package com.omgservers.application.operation.insertEventOperation;

import com.omgservers.application.module.internalModule.model.event.EventModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.time.Duration;

public interface InsertEventOperation {
    Uni<Void> insertEvent(SqlConnection sqlConnection, EventModel event);

    default void insertEvent(long timeout, PgPool pgPool, EventModel event) {
        pgPool.withTransaction(sqlConnection -> insertEvent(sqlConnection, event))
                .await().atMost(Duration.ofSeconds(timeout));
    }
}
