package com.omgservers.base.impl.operation.selectEventOperation;

import com.omgservers.model.event.EventModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.time.Duration;

public interface SelectEventOperation {
    Uni<EventModel> selectEvent(SqlConnection sqlConnection, Long id);

    default EventModel selectEvent(long timeout, PgPool pgPool, Long id) {
        return pgPool.withTransaction(sqlConnection -> selectEvent(sqlConnection, id))
                .await().atMost(Duration.ofSeconds(timeout));
    }
}
