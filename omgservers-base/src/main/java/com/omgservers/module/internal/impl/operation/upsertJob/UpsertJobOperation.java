package com.omgservers.module.internal.impl.operation.upsertJob;

import com.omgservers.model.job.JobModel;
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
