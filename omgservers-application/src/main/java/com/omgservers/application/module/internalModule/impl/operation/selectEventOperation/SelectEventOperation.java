package com.omgservers.application.module.internalModule.impl.operation.selectEventOperation;

import com.omgservers.application.module.internalModule.model.event.EventModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.time.Duration;
import java.util.UUID;

public interface SelectEventOperation {
    Uni<EventModel> selectEvent(SqlConnection sqlConnection, UUID uuid);

    default EventModel selectEvent(long timeout, PgPool pgPool, UUID uuid) {
        return pgPool.withTransaction(sqlConnection -> selectEvent(sqlConnection, uuid))
                .await().atMost(Duration.ofSeconds(timeout));
    }
}
