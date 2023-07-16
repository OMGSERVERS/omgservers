package com.omgservers.application.module.matchmakerModule.impl.operation.deleteRequestOperation;

import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.time.Duration;
import java.util.UUID;

public interface DeleteRequestOperation {
    Uni<Boolean> deleteRequest(SqlConnection sqlConnection, int shard, UUID uuid);

    default Boolean deleteRequest(long timeout, PgPool pgPool, int shard, UUID uuid) {
        return pgPool.withTransaction(sqlConnection -> deleteRequest(sqlConnection, shard, uuid))
                .await().atMost(Duration.ofSeconds(timeout));
    }
}
