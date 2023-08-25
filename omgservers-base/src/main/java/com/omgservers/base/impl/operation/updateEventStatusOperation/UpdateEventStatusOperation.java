package com.omgservers.base.impl.operation.updateEventStatusOperation;

import com.omgservers.model.event.EventStatusEnum;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.time.Duration;

public interface UpdateEventStatusOperation {
    Uni<Boolean> updateEventStatus(SqlConnection sqlConnection, Long id, EventStatusEnum newStatus);

    default Boolean updateEventStatus(long timeout, PgPool pgPool, Long id, EventStatusEnum newStatus) {
        return pgPool.withTransaction(sqlConnection -> updateEventStatus(sqlConnection, id, newStatus))
                .await().atMost(Duration.ofSeconds(timeout));
    }
}
