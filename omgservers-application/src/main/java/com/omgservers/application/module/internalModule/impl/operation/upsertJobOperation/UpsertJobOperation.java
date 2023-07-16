package com.omgservers.application.module.internalModule.impl.operation.upsertJobOperation;

import com.omgservers.application.module.internalModule.model.job.JobModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.time.Duration;

public interface UpsertJobOperation {
    Uni<Boolean> upsertJob(SqlConnection sqlConnection, JobModel job);

    default Boolean upsertJob(long timeout, PgPool pgPool, JobModel job) {
        return pgPool.withTransaction(sqlConnection -> upsertJob(sqlConnection, job))
                .await().atMost(Duration.ofSeconds(timeout));
    }
}
