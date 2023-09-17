package com.omgservers.module.system.impl.operation.selectAllJobs;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.model.job.JobModel;
import com.omgservers.model.job.JobTypeEnum;
import com.omgservers.operation.executeSelectList.ExecuteSelectListOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.Row;
import io.vertx.mutiny.sqlclient.SqlConnection;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class SelectAllJobsOperationImpl implements SelectAllJobsOperation {

    final ExecuteSelectListOperation executeSelectListOperation;

    final ObjectMapper objectMapper;

    @Override
    public Uni<List<JobModel>> selectAllJobs(SqlConnection sqlConnection) {
        return executeSelectListOperation.executeSelectList(
                sqlConnection,
                0,
                """
                        select id, created, shard_key, entity_id, type
                        from internal.tab_job
                        """,
                List.of(),
                "Job",
                this::createJob);
    }

    JobModel createJob(Row row) {
        JobModel job = new JobModel();
        job.setId(row.getLong("id"));
        job.setCreated(row.getOffsetDateTime("created").toInstant());
        job.setShardKey(row.getLong("shard_key"));
        job.setEntityId(row.getLong("entity"));
        job.setType(JobTypeEnum.valueOf(row.getString("type")));
        return job;
    }
}
