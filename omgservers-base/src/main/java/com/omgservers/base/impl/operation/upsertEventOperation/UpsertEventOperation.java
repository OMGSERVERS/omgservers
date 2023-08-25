package com.omgservers.base.impl.operation.upsertEventOperation;

import com.omgservers.model.event.EventModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.time.Duration;

public interface UpsertEventOperation {
    Uni<Boolean> upsertEvent(SqlConnection sqlConnection, EventModel event);

    default Boolean upsertEvent(long timeout, PgPool pgPool, EventModel event) {
        return pgPool.withTransaction(sqlConnection -> upsertEvent(sqlConnection, event))
                .await().atMost(Duration.ofSeconds(timeout));
    }
}
