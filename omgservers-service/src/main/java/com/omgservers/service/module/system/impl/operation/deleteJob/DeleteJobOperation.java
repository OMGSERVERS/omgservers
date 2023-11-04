package com.omgservers.service.module.system.impl.operation.deleteJob;

import com.omgservers.model.job.JobQualifierEnum;
import com.omgservers.service.operation.changeWithContext.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.time.Duration;

public interface DeleteJobOperation {
    Uni<Boolean> deleteJob(ChangeContext<?> changeContext,
                           SqlConnection sqlConnection,
                           Long shardKey,
                           Long entityId,
                           JobQualifierEnum qualifier);

    default Boolean deleteJob(long timeout,
                              PgPool pgPool,
                              Long shardKey,
                              Long entityId,
                              JobQualifierEnum qualifier) {
        return Uni.createFrom().context(context -> {
                    final var changeContext = new ChangeContext<Boolean>(context);
                    return pgPool.withTransaction(sqlConnection ->
                            deleteJob(changeContext, sqlConnection, shardKey, entityId, qualifier));
                })
                .await().atMost(Duration.ofSeconds(timeout));
    }
}