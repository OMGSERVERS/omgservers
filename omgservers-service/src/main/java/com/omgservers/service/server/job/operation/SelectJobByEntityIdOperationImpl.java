package com.omgservers.service.server.job.operation;

import com.omgservers.schema.model.job.JobModel;
import com.omgservers.service.operation.server.SelectObjectOperation;
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
class SelectJobByEntityIdOperationImpl implements SelectJobByEntityIdOperation {

    final SelectObjectOperation selectObjectOperation;

    final JobModelMapper jobModelMapper;

    @Override
    public Uni<JobModel> selectJobByEntityId(final SqlConnection sqlConnection,
                                             final Long shardKey,
                                             final Long entityId) {
        return selectObjectOperation.selectObject(
                sqlConnection,
                """
                        select id, idempotency_key, created, modified, qualifier, shard_key, entity_id, deleted
                        from $shard.tab_job
                        where shard_key = $1 and entity_id = $2
                        limit 1
                        """,
                List.of(shardKey, entityId),
                "Job",
                jobModelMapper::fromRow);
    }
}
