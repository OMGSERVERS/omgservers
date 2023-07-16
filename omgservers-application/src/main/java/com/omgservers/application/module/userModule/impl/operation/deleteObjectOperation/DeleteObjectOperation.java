package com.omgservers.application.module.userModule.impl.operation.deleteObjectOperation;

import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.time.Duration;
import java.util.UUID;

public interface DeleteObjectOperation {
    Uni<Boolean> deleteObject(SqlConnection sqlConnection, int shard, UUID uuid);

    default Boolean deleteObject(long timeout, PgPool pgPool, int shard, UUID uuid) {
        return pgPool.withTransaction(sqlConnection -> deleteObject(sqlConnection, shard, uuid))
                .await().atMost(Duration.ofSeconds(timeout));
    }
}
