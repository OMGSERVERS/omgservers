package com.omgservers.application.module.runtimeModule.impl.operation.deleteRuntimeOperation;

import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.time.Duration;
import java.util.UUID;

public interface DeleteRuntimeOperation {
    Uni<Boolean> deleteRuntime(SqlConnection sqlConnection, int shard, Long id);

    default Boolean deleteRuntime(long timeout, PgPool pgPool, int shard, Long id) {
        return pgPool.withTransaction(sqlConnection -> deleteRuntime(sqlConnection, shard, id))
                .await().atMost(Duration.ofSeconds(timeout));
    }
}
