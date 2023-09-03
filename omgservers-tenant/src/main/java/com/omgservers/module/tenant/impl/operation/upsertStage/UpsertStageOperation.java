package com.omgservers.module.tenant.impl.operation.upsertStage;

import com.omgservers.ChangeContext;
import com.omgservers.model.stage.StageModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.time.Duration;

public interface UpsertStageOperation {
    Uni<Boolean> upsertStage(ChangeContext changeContext,
                             SqlConnection sqlConnection,
                             int shard,
                             Long tenantId,
                             StageModel stage);

    default Boolean upsertStage(long timeout,
                                PgPool pgPool,
                                int shard,
                                Long tenantId,
                                StageModel stage) {
        return Uni.createFrom().context(context -> {
                    final var changeContext = new ChangeContext(context);
                    return pgPool.withTransaction(sqlConnection ->
                            upsertStage(changeContext, sqlConnection, shard, tenantId, stage));
                })
                .await().atMost(Duration.ofSeconds(timeout));
    }
}
