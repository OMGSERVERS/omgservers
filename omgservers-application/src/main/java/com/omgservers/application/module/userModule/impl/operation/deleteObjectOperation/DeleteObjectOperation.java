package com.omgservers.application.module.userModule.impl.operation.deleteObjectOperation;

import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.time.Duration;

public interface DeleteObjectOperation {
    Uni<Boolean> deleteObject(SqlConnection sqlConnection, int shard, Long id);

    default Boolean deleteObject(long timeout, PgPool pgPool, int shard, Long id) {
        return pgPool.withTransaction(sqlConnection -> deleteObject(sqlConnection, shard, id))
                .await().atMost(Duration.ofSeconds(timeout));
    }
}
