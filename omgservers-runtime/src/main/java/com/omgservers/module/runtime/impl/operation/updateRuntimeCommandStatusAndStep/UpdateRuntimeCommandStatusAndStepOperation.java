package com.omgservers.module.runtime.impl.operation.updateRuntimeCommandStatusAndStep;

import com.omgservers.ChangeContext;
import com.omgservers.model.runtimeCommand.RuntimeCommandStatusEnum;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.time.Duration;

public interface UpdateRuntimeCommandStatusAndStepOperation {
    Uni<Boolean> updateRuntimeCommandStatusAndStep(ChangeContext<?> changeContext,
                                                   SqlConnection sqlConnection,
                                                   int shard,
                                                   Long id,
                                                   RuntimeCommandStatusEnum status,
                                                   Long step);

    default Boolean updateRuntimeCommandStatusAndStep(long timeout,
                                                      PgPool pgPool,
                                                      int shard,
                                                      Long id,
                                                      RuntimeCommandStatusEnum status,
                                                      Long step) {
        return Uni.createFrom().context(context -> {
                    final var changeContext = new ChangeContext<Boolean>(context);
                    return pgPool.withTransaction(sqlConnection -> updateRuntimeCommandStatusAndStep(
                            changeContext,
                            sqlConnection,
                            shard,
                            id,
                            status,
                            step));
                })
                .await().atMost(Duration.ofSeconds(timeout));
    }
}
