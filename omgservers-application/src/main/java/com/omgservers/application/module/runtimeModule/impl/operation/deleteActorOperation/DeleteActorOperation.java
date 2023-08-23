package com.omgservers.application.module.runtimeModule.impl.operation.deleteActorOperation;

import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.time.Duration;

public interface DeleteActorOperation {
    Uni<Boolean> deleteActor(SqlConnection sqlConnection, int shard, Long id);

    default Boolean deleteActor(long timeout, PgPool pgPool, int shard, Long id) {
        return pgPool.withTransaction(sqlConnection -> deleteActor(sqlConnection, shard, id))
                .await().atMost(Duration.ofSeconds(timeout));
    }
}
