package com.omgservers.service.server.job.operation;

import com.omgservers.schema.model.job.JobModel;
import com.omgservers.service.operation.server.SelectListOperation;
import com.omgservers.service.server.job.impl.mapper.JobModelMapper;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class SelectJobsOperationImpl implements SelectJobsOperation {

    final SelectListOperation selectListOperation;

    final JobModelMapper jobModelMapper;

    @Override
    public Uni<List<JobModel>> selectJobs(final SqlConnection sqlConnection) {
        return selectListOperation.selectList(
                sqlConnection,
                """
                        select id, idempotency_key, created, modified, qualifier, shard_key, entity_id, deleted
                        from $shard.tab_job
                        where deleted = false
                        order by id asc
                        """,
                List.of(),
                "Job",
                jobModelMapper::fromRow);
    }
}
