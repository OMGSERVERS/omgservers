package com.omgservers.application.module.tenantModule.impl.operation.deleteStageOperation;

import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.time.Duration;

public interface DeleteStageOperation {
    Uni<Boolean> deleteStage(SqlConnection sqlConnection, int shard, Long id);

    default Boolean deleteStage(long timeout, PgPool pgPool, int shard, Long id) {
        return pgPool.withTransaction(sqlConnection -> deleteStage(sqlConnection, shard, id))
                .await().atMost(Duration.ofSeconds(timeout));
    }
}
