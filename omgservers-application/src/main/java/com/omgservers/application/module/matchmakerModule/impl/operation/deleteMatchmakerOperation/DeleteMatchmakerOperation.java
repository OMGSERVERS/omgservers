package com.omgservers.application.module.matchmakerModule.impl.operation.deleteMatchmakerOperation;

import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.time.Duration;
import java.util.UUID;

public interface DeleteMatchmakerOperation {
    Uni<Boolean> deleteMatchmaker(SqlConnection sqlConnection, int shard, UUID uuid);

    default Boolean deleteMatchmaker(long timeout, PgPool pgPool, int shard, UUID uuid) {
        return pgPool.withTransaction(sqlConnection -> deleteMatchmaker(sqlConnection, shard, uuid))
                .await().atMost(Duration.ofSeconds(timeout));
    }
}
