package com.omgservers.module.user.impl.operation.deletePlayer;

import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.time.Duration;

public interface DeletePlayerOperation {
    Uni<Boolean> deletePlayer(SqlConnection sqlConnection, int shard, Long id);

    default Boolean deletePlayer(long timeout, PgPool pgPool, int shard, Long id) {
        return pgPool.withTransaction(sqlConnection -> deletePlayer(sqlConnection, shard, id))
                .await().atMost(Duration.ofSeconds(timeout));
    }
}