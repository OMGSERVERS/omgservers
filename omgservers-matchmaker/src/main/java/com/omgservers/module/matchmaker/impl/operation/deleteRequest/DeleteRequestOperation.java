package com.omgservers.module.matchmaker.impl.operation.deleteRequest;

import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.time.Duration;

public interface DeleteRequestOperation {
    Uni<Boolean> deleteRequest(SqlConnection sqlConnection, int shard, Long id);

    default Boolean deleteRequest(long timeout, PgPool pgPool, int shard, Long id) {
        return pgPool.withTransaction(sqlConnection -> deleteRequest(sqlConnection, shard, id))
                .await().atMost(Duration.ofSeconds(timeout));
    }
}
