package com.omgservers.application.module.matchmakerModule.impl.operation.deleteMatchOperation;

import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.time.Duration;
import java.util.UUID;

public interface DeleteMatchOperation {
    Uni<Boolean> deleteMatch(SqlConnection sqlConnection, int shard, UUID uuid);

    default Boolean deleteMatch(long timeout, PgPool pgPool, int shard, UUID uuid) {
        return pgPool.withTransaction(sqlConnection -> deleteMatch(sqlConnection, shard, uuid))
                .await().atMost(Duration.ofSeconds(timeout));
    }
}
