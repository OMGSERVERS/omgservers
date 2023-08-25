package com.omgservers.application.module.matchmakerModule.impl.operation.selectRequestOperation;

import com.omgservers.model.request.RequestModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.time.Duration;

public interface SelectRequestOperation {
    Uni<RequestModel> selectRequest(SqlConnection sqlConnection, int shard, Long id);

    default RequestModel selectRequest(long timeout, PgPool pgPool, int shard, Long id) {
        return pgPool.withTransaction(sqlConnection -> selectRequest(sqlConnection, shard, id))
                .await().atMost(Duration.ofSeconds(timeout));
    }
}
