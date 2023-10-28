package com.omgservers.module.user.impl.operation.selectClient;

import com.omgservers.model.client.ClientModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.time.Duration;

public interface SelectClientOperation {
    Uni<ClientModel> selectClient(SqlConnection sqlConnection,
                                  int shard,
                                  Long userId,
                                  Long id);

    default ClientModel selectClient(long timeout, PgPool pgPool, int shard, Long userId, Long id) {
        return pgPool.withTransaction(sqlConnection -> selectClient(sqlConnection, shard, userId, id))
                .await().atMost(Duration.ofSeconds(timeout));
    }
}