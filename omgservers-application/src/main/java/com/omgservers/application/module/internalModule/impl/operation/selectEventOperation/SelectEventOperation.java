package com.omgservers.application.module.internalModule.impl.operation.selectEventOperation;

import com.omgservers.application.module.internalModule.model.event.EventModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.time.Duration;
import java.util.UUID;

public interface SelectEventOperation {
    Uni<EventModel> selectEvent(SqlConnection sqlConnection, Long id);

    default EventModel selectEvent(long timeout, PgPool pgPool, Long id) {
        return pgPool.withTransaction(sqlConnection -> selectEvent(sqlConnection, id))
                .await().atMost(Duration.ofSeconds(timeout));
    }
}
