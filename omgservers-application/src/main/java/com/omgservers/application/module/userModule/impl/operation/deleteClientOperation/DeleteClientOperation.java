package com.omgservers.application.module.userModule.impl.operation.deleteClientOperation;

import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.time.Duration;
import java.util.UUID;

public interface DeleteClientOperation {
    Uni<Boolean> deleteClient(SqlConnection sqlConnection, int shard, Long id);

    default Boolean deleteClient(long timeout, PgPool pgPool, int shard, Long id) {
        return pgPool.withTransaction(sqlConnection -> deleteClient(sqlConnection, shard, id))
                .await().atMost(Duration.ofSeconds(timeout));
    }
}
