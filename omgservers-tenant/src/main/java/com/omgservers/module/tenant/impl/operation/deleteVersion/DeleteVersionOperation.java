package com.omgservers.module.tenant.impl.operation.deleteVersion;

import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.time.Duration;

public interface DeleteVersionOperation {
    Uni<Boolean> deleteVersion(SqlConnection sqlConnection, int shard, Long id);

    default Boolean deleteVersion(long timeout, PgPool pgPool, int shard, Long id) {
        return pgPool.withTransaction(sqlConnection -> deleteVersion(sqlConnection, shard, id))
                .await().atMost(Duration.ofSeconds(timeout));
    }
}
