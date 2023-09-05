package com.omgservers.module.runtime.impl.operation.updateRuntimeCurrentStep;

import com.omgservers.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.time.Duration;

public interface UpdateRuntimeCurrentStepOperation {
    Uni<Boolean> updateRuntimeCurrentStep(ChangeContext<?> changeContext,
                                          SqlConnection sqlConnection,
                                          int shard,
                                          Long id,
                                          Long currentStep);

    default Boolean updateRuntimeCurrentStep(long timeout,
                                             PgPool pgPool,
                                             int shard,
                                             Long id,
                                             Long currentStep) {
        return Uni.createFrom().context(context -> {
                    final var changeContext = new ChangeContext<Boolean>(context);
                    return pgPool.withTransaction(sqlConnection ->
                            updateRuntimeCurrentStep(changeContext, sqlConnection, shard, id, currentStep));
                })
                .await().atMost(Duration.ofSeconds(timeout));
    }
}
