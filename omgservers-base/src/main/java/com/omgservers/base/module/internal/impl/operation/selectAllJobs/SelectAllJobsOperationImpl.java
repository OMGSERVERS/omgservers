package com.omgservers.base.module.internal.impl.operation.selectAllJobs;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.exception.ServerSideBadRequestException;
import com.omgservers.model.job.JobModel;
import com.omgservers.model.job.JobType;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.Row;
import io.vertx.mutiny.sqlclient.RowSet;
import io.vertx.mutiny.sqlclient.SqlConnection;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class SelectAllJobsOperationImpl implements SelectAllJobsOperation {

    static private final String sql = """
            select id, created, shard_key, entity, type 
            from internal.tab_job
            """;

    final ObjectMapper objectMapper;

    @Override
    public Uni<List<JobModel>> selectAllJobs(SqlConnection sqlConnection) {
        if (sqlConnection == null) {
            throw new ServerSideBadRequestException("sqlConnection is null");
        }

        return sqlConnection.preparedQuery(sql)
                .execute()
                .map(RowSet::iterator)
                .map(iterator -> {
                    final var results = new ArrayList<JobModel>();
                    while (iterator.hasNext()) {
                        final var job = createJob(iterator.next());
                        results.add(job);
                    }
                    if (results.size() > 0) {
                        log.info("Jobs were found, size={}", results.size());
                    } else {
                        log.info("Jobs were not found");
                    }
                    return results;
                });
    }

    JobModel createJob(Row row) {
        JobModel job = new JobModel();
        job.setId(row.getLong("id"));
        job.setCreated(row.getOffsetDateTime("created").toInstant());
        job.setShardKey(row.getLong("shard_key"));
        job.setEntity(row.getLong("entity"));
        job.setType(JobType.valueOf(row.getString("type")));
        return job;
    }
}
