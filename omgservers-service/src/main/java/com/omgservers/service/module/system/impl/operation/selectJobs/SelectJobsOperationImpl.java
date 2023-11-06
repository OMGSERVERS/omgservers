package com.omgservers.service.module.system.impl.operation.selectJobs;

import com.omgservers.model.job.JobModel;
import com.omgservers.service.module.system.impl.mappers.JobModelMapper;
import com.omgservers.service.operation.selectList.SelectListOperation;
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
    public Uni<List<JobModel>> selectJobs(SqlConnection sqlConnection) {
        return selectListOperation.selectList(
                sqlConnection,
                0,
                """
                        select
                            id, created, modified, shard_key, entity_id, qualifier, deleted
                        from system.tab_job
                        """,
                List.of(),
                "Job",
                jobModelMapper::fromRow);
    }
}
