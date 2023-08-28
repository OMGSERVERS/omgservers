package com.omgservers.module.runtime.impl.operation.deleteRuntimeCommand;

import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.time.Duration;

public interface DeleteRuntimeCommandOperation {
    Uni<Boolean> deleteRuntimeCommand(SqlConnection sqlConnection, int shard, Long id);

    default Boolean deleteRuntimeCommand(long timeout, PgPool pgPool, int shard, Long id) {
        return pgPool.withTransaction(sqlConnection -> deleteRuntimeCommand(sqlConnection, shard, id))
                .await().atMost(Duration.ofSeconds(timeout));
    }
}
