package com.omgservers.module.runtime.impl.operation.updateRuntimeStepAndState;

import com.omgservers.model.runtime.RuntimeStateModel;
import com.omgservers.operation.changeWithContext.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.time.Duration;

public interface UpdateRuntimeStepAndStateOperation {
    Uni<Boolean> updateRuntimeStepAndState(ChangeContext<?> changeContext,
                                           SqlConnection sqlConnection,
                                           int shard,
                                           Long id,
                                           Long step,
                                           RuntimeStateModel state);

    default Boolean updateRuntimeStepAndState(long timeout,
                                              PgPool pgPool,
                                              int shard,
                                              Long id,
                                              Long step,
                                              RuntimeStateModel state) {
        return Uni.createFrom().context(context -> {
                    final var changeContext = new ChangeContext<Boolean>(context);
                    return pgPool.withTransaction(sqlConnection ->
                            updateRuntimeStepAndState(changeContext, sqlConnection, shard, id, step, state));
                })
                .await().atMost(Duration.ofSeconds(timeout));
    }
}
