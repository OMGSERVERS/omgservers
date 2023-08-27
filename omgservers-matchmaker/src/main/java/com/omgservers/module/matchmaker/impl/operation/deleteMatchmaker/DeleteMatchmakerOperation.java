package com.omgservers.module.matchmaker.impl.operation.deleteMatchmaker;

import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.time.Duration;

public interface DeleteMatchmakerOperation {
    Uni<Boolean> deleteMatchmaker(SqlConnection sqlConnection, int shard, Long id);

    default Boolean deleteMatchmaker(long timeout, PgPool pgPool, int shard, Long id) {
        return pgPool.withTransaction(sqlConnection -> deleteMatchmaker(sqlConnection, shard, id))
                .await().atMost(Duration.ofSeconds(timeout));
    }
}
