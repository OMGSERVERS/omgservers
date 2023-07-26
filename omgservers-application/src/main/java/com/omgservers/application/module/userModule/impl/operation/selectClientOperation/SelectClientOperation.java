package com.omgservers.application.module.userModule.impl.operation.selectClientOperation;

import com.omgservers.application.module.userModule.model.client.ClientModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.time.Duration;
import java.util.UUID;

public interface SelectClientOperation {
    Uni<ClientModel> selectClient(SqlConnection sqlConnection,
                                  int shard,
                                  Long id);

    default ClientModel selectClient(long timeout, PgPool pgPool, int shard, Long id) {
        return pgPool.withTransaction(sqlConnection -> selectClient(sqlConnection, shard, id))
                .await().atMost(Duration.ofSeconds(timeout));
    }
}
