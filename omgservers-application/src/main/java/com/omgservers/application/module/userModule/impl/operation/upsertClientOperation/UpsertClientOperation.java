package com.omgservers.application.module.userModule.impl.operation.upsertClientOperation;

import com.omgservers.application.module.userModule.model.client.ClientModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.time.Duration;

public interface UpsertClientOperation {
    Uni<Boolean> upsertClient(SqlConnection sqlConnection, int shard, ClientModel client);

    default Boolean upsertClient(long timeout, PgPool pgPool, int shard, ClientModel client) {
        return pgPool.withTransaction(sqlConnection -> upsertClient(sqlConnection, shard, client))
                .await().atMost(Duration.ofSeconds(timeout));
    }
}
