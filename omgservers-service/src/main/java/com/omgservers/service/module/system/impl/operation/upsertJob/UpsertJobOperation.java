package com.omgservers.service.module.system.impl.operation.upsertJob;

import com.omgservers.service.operation.changeWithContext.ChangeContext;
import com.omgservers.model.job.JobModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.time.Duration;

public interface UpsertJobOperation {
    Uni<Boolean> upsertJob(ChangeContext<?> changeContext,
                           SqlConnection sqlConnection,
                           JobModel job);

    default Boolean upsertJob(long timeout, PgPool pgPool, JobModel job) {
        return Uni.createFrom().context(context -> {
                    final var changeContext = new ChangeContext<Boolean>(context);
                    return pgPool.withTransaction(sqlConnection ->
                            upsertJob(changeContext, sqlConnection, job));
                })
                .await().atMost(Duration.ofSeconds(timeout));
    }
}
