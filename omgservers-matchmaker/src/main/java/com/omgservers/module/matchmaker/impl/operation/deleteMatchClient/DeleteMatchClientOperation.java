package com.omgservers.module.matchmaker.impl.operation.deleteMatchClient;

import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.time.Duration;

public interface DeleteMatchClientOperation {
    Uni<Boolean> deleteMatchClient(SqlConnection sqlConnection, int shard, Long id);

    default Boolean deleteMatchClient(long timeout, PgPool pgPool, int shard, Long id) {
        return pgPool.withTransaction(sqlConnection -> deleteMatchClient(sqlConnection, shard, id))
                .await().atMost(Duration.ofSeconds(timeout));
    }
}
