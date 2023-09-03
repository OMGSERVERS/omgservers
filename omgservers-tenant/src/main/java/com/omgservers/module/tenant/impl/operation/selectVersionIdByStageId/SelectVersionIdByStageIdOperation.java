package com.omgservers.module.tenant.impl.operation.selectVersionIdByStageId;

import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.time.Duration;

public interface SelectVersionIdByStageIdOperation {
    Uni<Long> selectVersionIdByStageId(SqlConnection sqlConnection,
                                       int shard,
                                       Long stageId);

    default Long selectVersionIdByStageId(long timeout, PgPool pgPool, int shard, Long stageId) {
        return pgPool.withTransaction(sqlConnection -> selectVersionIdByStageId(sqlConnection, shard, stageId))
                .await().atMost(Duration.ofSeconds(timeout));
    }
}
