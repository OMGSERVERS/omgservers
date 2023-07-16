package com.omgservers.application.module.matchmakerModule.impl.operation.insertRequestOperation;

import com.omgservers.application.module.matchmakerModule.model.request.RequestModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.time.Duration;

public interface InsertRequestOperation {
    Uni<Void> insertRequest(SqlConnection sqlConnection, int shard, RequestModel request);

    default void insertRequest(long timeout, PgPool pgPool, int shard, RequestModel request) {
        pgPool.withTransaction(sqlConnection -> insertRequest(sqlConnection, shard, request))
                .await().atMost(Duration.ofSeconds(timeout));
    }
}
