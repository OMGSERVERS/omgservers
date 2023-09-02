package com.omgservers.module.tenant.impl.operation.selectCurrentVersionId;

import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.time.Duration;

public interface SelectCurrentVersionIdOperation {
    Uni<Long> selectCurrentVersionId(SqlConnection sqlConnection,
                                     int shard,
                                     Long stageId);

    default Long selectCurrentVersionId(long timeout, PgPool pgPool, int shard, Long stageId) {
        return pgPool.withTransaction(sqlConnection -> selectCurrentVersionId(sqlConnection, shard, stageId))
                .await().atMost(Duration.ofSeconds(timeout));
    }
}
