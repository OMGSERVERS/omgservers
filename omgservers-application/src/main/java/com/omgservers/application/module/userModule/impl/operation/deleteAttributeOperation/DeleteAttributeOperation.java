package com.omgservers.application.module.userModule.impl.operation.deleteAttributeOperation;

import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.time.Duration;
import java.util.UUID;

public interface DeleteAttributeOperation {
    Uni<Boolean> deleteAttribute(SqlConnection sqlConnection, int shard, Long playerId, String name);

    default Boolean deleteAttribute(long timeout, PgPool pgPool, int shard, Long playerId, String name) {
        return pgPool.withTransaction(sqlConnection -> deleteAttribute(sqlConnection, shard, playerId, name))
                .await().atMost(Duration.ofSeconds(timeout));
    }
}
