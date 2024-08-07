package com.omgservers.service.server.service.job.operation.selectJobByEntityId;

import com.omgservers.schema.model.job.JobModel;
import com.omgservers.service.server.mappers.JobModelMapper;
import com.omgservers.service.server.operation.selectObject.SelectObjectOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class SelectJobByEntityIdOperationImpl implements SelectJobByEntityIdOperation {

    final SelectObjectOperation selectObjectOperation;

    final JobModelMapper jobModelMapper;

    @Override
    public Uni<JobModel> selectJobByEntityId(final SqlConnection sqlConnection, Long entityId) {
        return selectObjectOperation.selectObject(
                sqlConnection,
                0,
                """
                        select id, idempotency_key, created, modified, qualifier, entity_id, deleted
                        from system.tab_job
                        where entity_id = $1
                        limit 1
                        """,
                List.of(entityId),
                "Job",
                jobModelMapper::fromRow);
    }
}
