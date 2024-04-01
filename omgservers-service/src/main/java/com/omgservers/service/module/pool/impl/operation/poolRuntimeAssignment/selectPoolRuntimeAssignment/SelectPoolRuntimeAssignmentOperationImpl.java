package com.omgservers.service.module.pool.impl.operation.poolRuntimeAssignment.selectPoolRuntimeAssignment;

import com.omgservers.model.poolRuntimeAssignment.PoolRuntimeAssignmentModel;
import com.omgservers.service.module.pool.impl.mappers.PoolRuntimeAssignmentModelMapper;
import com.omgservers.service.operation.selectObject.SelectObjectOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class SelectPoolRuntimeAssignmentOperationImpl implements SelectPoolRuntimeAssignmentOperation {

    final SelectObjectOperation selectObjectOperation;

    final PoolRuntimeAssignmentModelMapper poolRuntimeAssignmentModelMapper;

    @Override
    public Uni<PoolRuntimeAssignmentModel> selectPoolRuntimeAssignment(
            final SqlConnection sqlConnection,
            final int shard,
            final Long poolId,
            final Long id) {
        return selectObjectOperation.selectObject(
                sqlConnection,
                shard,
                """
                        select
                            id, idempotency_key, pool_id, created, modified, runtime_id, server_id, config, deleted
                        from $schema.tab_pool_runtime_assignment
                        where pool_id = $1 and id = $2
                        limit 1
                        """,
                List.of(poolId, id),
                "Pool runtime assignment",
                poolRuntimeAssignmentModelMapper::fromRow);
    }
}
