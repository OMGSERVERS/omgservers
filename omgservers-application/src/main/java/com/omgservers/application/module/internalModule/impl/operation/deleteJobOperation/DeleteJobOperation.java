package com.omgservers.application.module.internalModule.impl.operation.deleteJobOperation;

import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.time.Duration;
import java.util.UUID;

public interface DeleteJobOperation {
    Uni<Boolean> deleteJob(SqlConnection sqlConnection, UUID shardKey, UUID entity);

    default Boolean deleteJob(long timeout, PgPool pgPool, UUID shardKey, UUID entity) {
        return pgPool.withTransaction(sqlConnection -> deleteJob(sqlConnection, shardKey, entity))
                .await().atMost(Duration.ofSeconds(timeout));
    }
}
