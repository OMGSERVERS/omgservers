package com.omgservers.module.internal.impl.operation.upsertJob;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.exception.ServerSideBadRequestException;
import com.omgservers.exception.ServerSideConflictException;
import com.omgservers.model.job.JobModel;
import com.omgservers.operation.transformPgException.TransformPgExceptionOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;
import io.vertx.mutiny.sqlclient.Tuple;
import io.vertx.pgclient.PgException;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.ZoneOffset;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class UpsertJobOperationImpl implements UpsertJobOperation {

    static private final String sql = """
            insert into internal.tab_job(id, created, shard_key, entity, type)
            values($1, $2, $3, $4, $5)
            on conflict (id) do
            nothing
            """;

    final TransformPgExceptionOperation transformPgExceptionOperation;

    final ObjectMapper objectMapper;

    @Override
    public Uni<Boolean> upsertJob(final SqlConnection sqlConnection, final JobModel job) {
        if (sqlConnection == null) {
            throw new ServerSideBadRequestException("sqlConnection is null");
        }
        if (job == null) {
            throw new ServerSideBadRequestException("job is null");
        }

        return upsertQuery(sqlConnection, job)
                .invoke(inserted -> {
                    if (inserted) {
                        log.info("Job was inserted, job={}", job);
                    } else {
                        log.warn("Job was already inserted, skip operation, job={}", job);
                    }
                })
                .onFailure(PgException.class)
                .transform(t -> transformPgExceptionOperation.transformPgException((PgException) t));
    }

    Uni<Boolean> upsertQuery(SqlConnection sqlConnection, JobModel job) {
        return sqlConnection.preparedQuery(sql)
                .execute(Tuple.of(
                        job.getId(),
                        job.getCreated().atOffset(ZoneOffset.UTC),
                        job.getShardKey(),
                        job.getEntity(),
                        job.getType()))
                .map(rowSet -> rowSet.rowCount() > 0);
    }
}
