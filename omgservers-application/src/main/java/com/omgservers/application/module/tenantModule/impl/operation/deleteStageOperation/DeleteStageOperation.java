package com.omgservers.application.module.tenantModule.impl.operation.deleteStageOperation;

import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.time.Duration;
import java.util.List;
import java.util.UUID;

public interface DeleteStageOperation {
    Uni<Boolean> deleteStage(SqlConnection sqlConnection, int shard, UUID uuid);

    default Boolean deleteStage(long timeout, PgPool pgPool, int shard, UUID uuid) {
        return pgPool.withTransaction(sqlConnection -> deleteStage(sqlConnection, shard, uuid))
                .await().atMost(Duration.ofSeconds(timeout));
    }
}
