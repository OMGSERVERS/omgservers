package com.omgservers.base.module.internal.impl.operation.selectNewEvents;

import com.omgservers.model.event.EventModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.time.Duration;
import java.util.List;

public interface SelectNewEventsOperation {
    Uni<List<EventModel>> selectNewEvents(SqlConnection sqlConnection, int limit);

    default List<EventModel> selectNewEvents(long timeout, PgPool pgPool, int limit) {
        return pgPool.withTransaction(sqlConnection -> selectNewEvents(sqlConnection, limit))
                .await().atMost(Duration.ofSeconds(timeout));
    }
}
