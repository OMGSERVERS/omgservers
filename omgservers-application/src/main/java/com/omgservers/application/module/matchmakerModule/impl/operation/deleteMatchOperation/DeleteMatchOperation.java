package com.omgservers.application.module.matchmakerModule.impl.operation.deleteMatchOperation;

import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.time.Duration;

public interface DeleteMatchOperation {
    Uni<Boolean> deleteMatch(SqlConnection sqlConnection, int shard, Long id);

    default Boolean deleteMatch(long timeout, PgPool pgPool, int shard, Long id) {
        return pgPool.withTransaction(sqlConnection -> deleteMatch(sqlConnection, shard, id))
                .await().atMost(Duration.ofSeconds(timeout));
    }
}
