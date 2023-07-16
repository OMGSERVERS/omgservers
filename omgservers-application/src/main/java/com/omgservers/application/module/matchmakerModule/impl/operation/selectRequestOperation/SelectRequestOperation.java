package com.omgservers.application.module.matchmakerModule.impl.operation.selectRequestOperation;

import com.omgservers.application.module.matchmakerModule.model.request.RequestModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.time.Duration;
import java.util.UUID;

public interface SelectRequestOperation {
    Uni<RequestModel> selectRequest(SqlConnection sqlConnection, int shard, UUID uuid);

    default RequestModel selectRequest(long timeout, PgPool pgPool, int shard, UUID uuid) {
        return pgPool.withTransaction(sqlConnection -> selectRequest(sqlConnection, shard, uuid))
                .await().atMost(Duration.ofSeconds(timeout));
    }
}
