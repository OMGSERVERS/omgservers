package com.omgservers.module.runtime.impl.operation.selectRuntimeGrantsByRuntimeId;

import com.omgservers.model.runtimeGrant.RuntimeGrantModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.time.Duration;
import java.util.List;

public interface SelectRuntimeGrantsByRuntimeIdOperation {
    Uni<List<RuntimeGrantModel>> selectRuntimeGrantsByRuntimeId(SqlConnection sqlConnection,
                                                                int shard,
                                                                Long runtimeId);

    default List<RuntimeGrantModel> selectRuntimeGrantsByRuntimeId(long timeout,
                                                                   PgPool pgPool,
                                                                   int shard,
                                                                   Long runtimeId) {
        return pgPool.withTransaction(
                        sqlConnection -> selectRuntimeGrantsByRuntimeId(
                                sqlConnection, shard, runtimeId))
                .await().atMost(Duration.ofSeconds(timeout));
    }
}
