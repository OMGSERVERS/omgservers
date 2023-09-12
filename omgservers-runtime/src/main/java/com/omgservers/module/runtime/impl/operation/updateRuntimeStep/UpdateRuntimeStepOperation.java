package com.omgservers.module.runtime.impl.operation.updateRuntimeStep;

import com.omgservers.operation.changeWithContext.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.time.Duration;

public interface UpdateRuntimeStepOperation {
    Uni<Boolean> updateRuntimeStep(ChangeContext<?> changeContext,
                                   SqlConnection sqlConnection,
                                   int shard,
                                   Long id,
                                   Long step);

    default Boolean updateRuntimeStep(long timeout,
                                      PgPool pgPool,
                                      int shard,
                                      Long id,
                                      Long step) {
        return Uni.createFrom().context(context -> {
                    final var changeContext = new ChangeContext<Boolean>(context);
                    return pgPool.withTransaction(sqlConnection ->
                            updateRuntimeStep(changeContext, sqlConnection, shard, id, step));
                })
                .await().atMost(Duration.ofSeconds(timeout));
    }
}
