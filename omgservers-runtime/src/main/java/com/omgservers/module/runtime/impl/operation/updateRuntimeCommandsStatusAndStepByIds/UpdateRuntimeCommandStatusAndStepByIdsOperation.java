package com.omgservers.module.runtime.impl.operation.updateRuntimeCommandsStatusAndStepByIds;

import com.omgservers.model.runtimeCommand.RuntimeCommandStatusEnum;
import com.omgservers.operation.changeWithContext.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.time.Duration;
import java.util.List;

public interface UpdateRuntimeCommandStatusAndStepByIdsOperation {
    Uni<Boolean> updateRuntimeCommandStatusAndStepByIds(ChangeContext<?> changeContext,
                                                        SqlConnection sqlConnection,
                                                        int shard,
                                                        List<Long> ids,
                                                        RuntimeCommandStatusEnum status,
                                                        Long step);

    default Boolean updateRuntimeCommandStatusAndStepByIds(long timeout,
                                                           PgPool pgPool,
                                                           int shard,
                                                           List<Long> ids,
                                                           RuntimeCommandStatusEnum status,
                                                           Long step) {
        return Uni.createFrom().context(context -> {
                    final var changeContext = new ChangeContext<Boolean>(context);
                    return pgPool.withTransaction(sqlConnection -> updateRuntimeCommandStatusAndStepByIds(
                            changeContext,
                            sqlConnection,
                            shard,
                            ids,
                            status,
                            step));
                })
                .await().atMost(Duration.ofSeconds(timeout));
    }
}
