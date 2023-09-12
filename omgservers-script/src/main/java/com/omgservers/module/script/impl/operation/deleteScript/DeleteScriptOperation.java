package com.omgservers.module.script.impl.operation.deleteScript;

import com.omgservers.operation.changeWithContext.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.time.Duration;

public interface DeleteScriptOperation {
    Uni<Boolean> deleteScript(ChangeContext<?> changeContext,
                              SqlConnection sqlConnection,
                              int shard,
                              Long id);

    default Boolean deleteScript(long timeout, PgPool pgPool, int shard, Long id) {
        return Uni.createFrom().context(context -> {
                    final var changeContext = new ChangeContext<Boolean>(context);
                    return pgPool.withTransaction(sqlConnection ->
                            deleteScript(changeContext, sqlConnection, shard, id));
                })
                .await().atMost(Duration.ofSeconds(timeout));
    }
}
