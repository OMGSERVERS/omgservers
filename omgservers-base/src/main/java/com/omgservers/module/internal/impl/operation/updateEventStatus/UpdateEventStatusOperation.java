package com.omgservers.module.internal.impl.operation.updateEventStatus;

import com.omgservers.operation.changeWithContext.ChangeContext;
import com.omgservers.model.event.EventStatusEnum;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.time.Duration;

public interface UpdateEventStatusOperation {
    Uni<Boolean> updateEventStatus(ChangeContext<?> changeContext, SqlConnection sqlConnection, Long id, EventStatusEnum newStatus);

    default Boolean updateEventStatus(long timeout, PgPool pgPool, Long id, EventStatusEnum newStatus) {
        return Uni.createFrom().context(context -> {
                    final var changeContext = new ChangeContext<Boolean>(context);
                    return pgPool.withTransaction(sqlConnection ->
                            updateEventStatus(changeContext, sqlConnection, id, newStatus));
                })
                .await().atMost(Duration.ofSeconds(timeout));
    }
}
