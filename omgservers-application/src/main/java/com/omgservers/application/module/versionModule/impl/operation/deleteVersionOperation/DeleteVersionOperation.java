package com.omgservers.application.module.versionModule.impl.operation.deleteVersionOperation;

import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.time.Duration;
import java.util.UUID;

public interface DeleteVersionOperation {
    Uni<Boolean> deleteVersion(SqlConnection sqlConnection, int shard, UUID uuid);

    default Boolean deleteVersion(long timeout, PgPool pgPool, int shard, UUID uuid) {
        return pgPool.withTransaction(sqlConnection -> deleteVersion(sqlConnection, shard, uuid))
                .await().atMost(Duration.ofSeconds(timeout));
    }
}
