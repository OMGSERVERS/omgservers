package com.omgservers.module.tenant.impl.operation.deleteStage;

import com.omgservers.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.time.Duration;

public interface DeleteStageOperation {
    Uni<Boolean> deleteStage(ChangeContext<?> changeContext,
                             SqlConnection sqlConnection,
                             int shard,
                             Long tenantId,
                             Long id);

    default Boolean deleteStage(long timeout, PgPool pgPool, int shard, Long tenantId, Long id) {
        return Uni.createFrom().context(context -> {
                    final var changeContext = new ChangeContext<Boolean>(context);
                    return pgPool.withTransaction(sqlConnection ->
                            deleteStage(changeContext, sqlConnection, shard, tenantId, id));
                })
                .await().atMost(Duration.ofSeconds(timeout));
    }
}
