package com.omgservers.module.internal.impl.operation.deleteIndex;

import com.omgservers.operation.changeWithContext.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.time.Duration;

public interface DeleteIndexOperation {
    Uni<Boolean> deleteIndex(ChangeContext<?> changeContext,
                             SqlConnection sqlConnection,
                             Long id);

    default Boolean deleteIndex(long timeout, PgPool pgPool, Long id) {
        return Uni.createFrom().context(context -> {
                    final var changeContext = new ChangeContext<Boolean>(context);
                    return pgPool.withTransaction(sqlConnection ->
                            deleteIndex(changeContext, sqlConnection, id));
                })
                .await().atMost(Duration.ofSeconds(timeout));
    }
}
