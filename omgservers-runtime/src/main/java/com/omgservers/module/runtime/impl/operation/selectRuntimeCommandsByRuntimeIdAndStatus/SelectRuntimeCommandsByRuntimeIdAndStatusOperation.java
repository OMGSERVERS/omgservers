package com.omgservers.module.runtime.impl.operation.selectRuntimeCommandsByRuntimeIdAndStatus;

import com.omgservers.model.runtimeCommand.RuntimeCommandModel;
import com.omgservers.model.runtimeCommand.RuntimeCommandStatusEnum;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.time.Duration;
import java.util.List;

public interface SelectRuntimeCommandsByRuntimeIdAndStatusOperation {
    Uni<List<RuntimeCommandModel>> selectRuntimeCommandsByRuntimeIdAndStatus(SqlConnection sqlConnection,
                                                                             int shard,
                                                                             Long runtimeId,
                                                                             RuntimeCommandStatusEnum status);

    default List<RuntimeCommandModel> selectRuntimeCommandsByRuntimeIdAndStatus(long timeout,
                                                                                PgPool pgPool,
                                                                                int shard,
                                                                                Long runtimeId,
                                                                                RuntimeCommandStatusEnum status) {
        return pgPool.withTransaction(sqlConnection -> selectRuntimeCommandsByRuntimeIdAndStatus(sqlConnection, shard, runtimeId, status))
                .await().atMost(Duration.ofSeconds(timeout));
    }
}
