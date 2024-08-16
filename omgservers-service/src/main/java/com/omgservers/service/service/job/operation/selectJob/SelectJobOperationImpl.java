package com.omgservers.service.service.job.operation.selectJob;

import com.omgservers.schema.model.job.JobModel;
import com.omgservers.service.service.job.impl.mapper.JobModelMapper;
import com.omgservers.service.operation.selectObject.SelectObjectOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Collections;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class SelectJobOperationImpl implements SelectJobOperation {

    final SelectObjectOperation selectObjectOperation;

    final JobModelMapper jobModelMapper;

    @Override
    public Uni<JobModel> selectJob(final SqlConnection sqlConnection,
                                   final Long id) {
        return selectObjectOperation.selectObject(
                sqlConnection,
                0,
                """
                        select id, idempotency_key, created, modified, qualifier, shard_key, entity_id, deleted
                        from system.tab_job
                        where id = $1
                        limit 1
                        """,
                Collections.singletonList(id),
                "Job",
                jobModelMapper::fromRow);
    }
}
