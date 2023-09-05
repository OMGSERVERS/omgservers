package com.omgservers.module.internal.impl.operation.upsertEvent;

import com.omgservers.ChangeContext;
import com.omgservers.model.event.EventModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.time.Duration;

public interface UpsertEventOperation {
    Uni<Boolean> upsertEvent(ChangeContext<?> changeContext,
                             SqlConnection sqlConnection,
                             EventModel event);

    default Boolean upsertEvent(long timeout, PgPool pgPool, EventModel event) {
        return Uni.createFrom().context(context -> {
                    final var changeContext = new ChangeContext<Boolean>(context);
                    return pgPool.withTransaction(sqlConnection ->
                            upsertEvent(changeContext, sqlConnection, event));
                })
                .await().atMost(Duration.ofSeconds(timeout));
    }
}
