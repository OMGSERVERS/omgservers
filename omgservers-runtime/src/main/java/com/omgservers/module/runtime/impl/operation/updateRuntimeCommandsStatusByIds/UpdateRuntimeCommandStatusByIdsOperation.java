package com.omgservers.module.runtime.impl.operation.updateRuntimeCommandsStatusByIds;

import com.omgservers.model.runtimeCommand.RuntimeCommandStatusEnum;
import com.omgservers.operation.changeWithContext.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.time.Duration;
import java.util.List;

public interface UpdateRuntimeCommandStatusByIdsOperation {
    Uni<Boolean> updateRuntimeCommandStatusByIds(ChangeContext<?> changeContext,
                                                 SqlConnection sqlConnection,
                                                 int shard,
                                                 Long runtimeId,
                                                 List<Long> ids,
                                                 RuntimeCommandStatusEnum status);

    default Boolean updateRuntimeCommandStatusByIds(long timeout,
                                                    PgPool pgPool,
                                                    int shard,
                                                    Long runtimeId,
                                                    List<Long> ids,
                                                    RuntimeCommandStatusEnum status) {
        return Uni.createFrom().context(context -> {
                    final var changeContext = new ChangeContext<Boolean>(context);
                    return pgPool.withTransaction(sqlConnection -> updateRuntimeCommandStatusByIds(
                            changeContext,
                            sqlConnection,
                            shard,
                            runtimeId,
                            ids,
                            status));
                })
                .await().atMost(Duration.ofSeconds(timeout));
    }
}
