package com.omgservers.module.script.impl.operation.updateScriptState;

import com.omgservers.operation.changeWithContext.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.time.Duration;

public interface UpdateScriptStateOperation {
    Uni<Boolean> updateScriptState(ChangeContext<?> changeContext,
                                   SqlConnection sqlConnection,
                                   int shard,
                                   Long id,
                                   String state);

    default Boolean updateScriptState(long timeout,
                                      PgPool pgPool,
                                      int shard,
                                      Long id,
                                      String state) {
        return Uni.createFrom().context(context -> {
                    final var changeContext = new ChangeContext<Boolean>(context);
                    return pgPool.withTransaction(sqlConnection ->
                            updateScriptState(changeContext, sqlConnection, shard, id, state));
                })
                .await().atMost(Duration.ofSeconds(timeout));
    }
}
