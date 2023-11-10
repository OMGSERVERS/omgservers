package com.omgservers.service.module.system.impl.operation.selectJob;

import com.omgservers.model.job.JobModel;
import com.omgservers.service.module.system.impl.mappers.JobModelMapper;
import com.omgservers.service.operation.selectObject.SelectObjectOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.Collections;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class selectJobOperationImpl implements SelectJobOperation {

    final SelectObjectOperation selectObjectOperation;

    final JobModelMapper jobModelMapper;

    @Override
    public Uni<JobModel> selectJob(final SqlConnection sqlConnection,
                                   final Long id) {
        return selectObjectOperation.selectObject(
                sqlConnection,
                0,
                """
                        select
                            id, created, modified, shard_key, entity_id, qualifier, deleted
                        from system.tab_job
                        where id = $1
                        limit 1
                        """,
                Collections.singletonList(id),
                "Job",
                jobModelMapper::fromRow);
    }
}
