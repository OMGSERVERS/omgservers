package com.omgservers.module.internal.impl.operation.deleteJob;

import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.time.Duration;

public interface DeleteJobOperation {
    Uni<Boolean> deleteJob(SqlConnection sqlConnection, Long shardKey, Long entity);

    default Boolean deleteJob(long timeout, PgPool pgPool, Long shardKey, Long entity) {
        return pgPool.withTransaction(sqlConnection -> deleteJob(sqlConnection, shardKey, entity))
                .await().atMost(Duration.ofSeconds(timeout));
    }
}
