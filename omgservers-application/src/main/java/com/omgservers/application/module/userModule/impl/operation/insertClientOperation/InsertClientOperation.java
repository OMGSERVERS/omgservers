package com.omgservers.application.module.userModule.impl.operation.insertClientOperation;

import com.omgservers.application.module.userModule.model.client.ClientModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.time.Duration;

public interface InsertClientOperation {
    Uni<Void> insertClient(SqlConnection sqlConnection, int shard, ClientModel client);

    default void insertClient(long timeout, PgPool pgPool, int shard, ClientModel client) {
        pgPool.withTransaction(sqlConnection -> insertClient(sqlConnection, shard, client))
                .await().atMost(Duration.ofSeconds(timeout));
    }
}
