package com.omgservers.service.module.system.impl.operation.selectEventProjectionsByStatus;

import com.omgservers.model.event.EventStatusEnum;
import com.omgservers.model.eventProjection.EventProjectionModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.time.Duration;
import java.util.List;

public interface SelectEventProjectionsByStatusAndRelayedOperation {
    Uni<List<EventProjectionModel>> selectEventProjectionsByStatusAndRelayed(SqlConnection sqlConnection,
                                                                             EventStatusEnum status,
                                                                             Boolean relayed,
                                                                             int limit);

    default List<EventProjectionModel> selectEventProjectionsByStatusAndRelayed(long timeout,
                                                                                PgPool pgPool,
                                                                                EventStatusEnum status,
                                                                                Boolean relayed,
                                                                                int limit) {
        return pgPool.withTransaction(
                        sqlConnection -> selectEventProjectionsByStatusAndRelayed(sqlConnection, status, relayed, limit))
                .await().atMost(Duration.ofSeconds(timeout));
    }
}
