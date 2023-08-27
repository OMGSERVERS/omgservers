package com.omgservers.module.matchmaker.impl.operation.upsertRequest;

import com.omgservers.model.request.RequestModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.time.Duration;

public interface UpsertRequestOperation {
    Uni<Boolean> upsertRequest(SqlConnection sqlConnection, int shard, RequestModel request);

    default Boolean upsertRequest(long timeout, PgPool pgPool, int shard, RequestModel request) {
        return pgPool.withTransaction(sqlConnection -> upsertRequest(sqlConnection, shard, request))
                .await().atMost(Duration.ofSeconds(timeout));
    }
}
