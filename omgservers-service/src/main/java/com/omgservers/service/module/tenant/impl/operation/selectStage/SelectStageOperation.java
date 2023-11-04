package com.omgservers.service.module.tenant.impl.operation.selectStage;

import com.omgservers.model.stage.StageModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.time.Duration;

public interface SelectStageOperation {
    Uni<StageModel> selectStage(SqlConnection sqlConnection,
                                int shard,
                                Long tenantId,
                                Long id);

    default StageModel selectStage(long timeout, PgPool pgPool, int shard, Long tenantId, Long id) {
        return pgPool.withTransaction(sqlConnection -> selectStage(sqlConnection, shard, tenantId, id))
                .await().atMost(Duration.ofSeconds(timeout));
    }
}
