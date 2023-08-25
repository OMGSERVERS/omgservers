package com.omgservers.application.module.tenantModule.impl.operation.upsertStageOperation;

import com.omgservers.model.stage.StageModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.time.Duration;

public interface UpsertStageOperation {
    Uni<Boolean> upsertStage(SqlConnection sqlConnection, int shard, StageModel stage);

    default Boolean upsertStage(long timeout, PgPool pgPool, int shard, StageModel stage) {
        return pgPool.withTransaction(sqlConnection -> upsertStage(sqlConnection, shard, stage))
                .await().atMost(Duration.ofSeconds(timeout));
    }
}
