package com.omgservers.service.module.system.impl.operation.deleteEvent;

import com.omgservers.service.operation.changeWithContext.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.time.Duration;

public interface DeleteEventOperation {
    Uni<Boolean> deleteEvent(ChangeContext<?> changeContext, SqlConnection sqlConnection, Long id);

    default Boolean deleteEvent(long timeout, PgPool pgPool, Long id) {
        return Uni.createFrom().context(context -> {
                    final var changeContext = new ChangeContext<Boolean>(context);
                    return pgPool.withTransaction(sqlConnection ->
                            deleteEvent(changeContext, sqlConnection, id));
                })
                .await().atMost(Duration.ofSeconds(timeout));
    }
}
