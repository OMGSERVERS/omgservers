package com.omgservers.module.runtime.impl.operation.selectRuntimeGrantsByRuntimeIdAndEntityIds;

import com.omgservers.model.runtimeGrant.RuntimeGrantModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.time.Duration;
import java.util.List;

public interface SelectRuntimeGrantsByRuntimeIdAndEntityIdsOperation {
    Uni<List<RuntimeGrantModel>> selectRuntimeGrantsByRuntimeIdAndEntityIds(SqlConnection sqlConnection,
                                                                            int shard,
                                                                            Long runtimeId,
                                                                            List<Long> entityIds);

    default List<RuntimeGrantModel> selectRuntimeGrantsByRuntimeIdAndEntityIds(long timeout,
                                                                               PgPool pgPool,
                                                                               int shard,
                                                                               Long runtimeId,
                                                                               List<Long> entityIds) {
        return pgPool.withTransaction(
                        sqlConnection -> selectRuntimeGrantsByRuntimeIdAndEntityIds(
                                sqlConnection, shard, runtimeId, entityIds))
                .await().atMost(Duration.ofSeconds(timeout));
    }
}
