package com.omgservers.application.module.tenantModule.impl.operation.insertStageOperation;

import com.omgservers.application.module.tenantModule.model.stage.StageModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.time.Duration;

public interface InsertStageOperation {
    Uni<Void> insertStage(SqlConnection sqlConnection, int shard, StageModel stage);

    default void insertStage(long timeout, PgPool pgPool, int shard, StageModel stage) {
        pgPool.withTransaction(sqlConnection -> insertStage(sqlConnection, shard, stage))
                .await().atMost(Duration.ofSeconds(timeout));
    }
}
