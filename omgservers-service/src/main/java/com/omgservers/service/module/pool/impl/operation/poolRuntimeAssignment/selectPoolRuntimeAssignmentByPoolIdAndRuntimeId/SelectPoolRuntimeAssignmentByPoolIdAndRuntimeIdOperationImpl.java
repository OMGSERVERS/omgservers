package com.omgservers.service.module.pool.impl.operation.poolRuntimeAssignment.selectPoolRuntimeAssignmentByPoolIdAndRuntimeId;

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
class SelectPoolRuntimeAssignmentByPoolIdAndRuntimeIdOperationImpl
        implements SelectPoolRuntimeAssignmentByPoolIdAndRuntimeIdOperation {

    final SelectObjectOperation selectObjectOperation;

    final PoolRuntimeAssignmentModelMapper poolRuntimeAssignmentModelMapper;

    @Override
    public Uni<PoolRuntimeAssignmentModel> selectPoolRuntimeAssignmentByPoolIdAndRuntimeId(
            final SqlConnection sqlConnection,
            final int shard,
            final Long poolId,
            final Long runtimeId) {
        return selectObjectOperation.selectObject(
                sqlConnection,
                shard,
                """
                        select
                            id, idempotency_key, pool_id, created, modified, runtime_id, server_id, config, deleted
                        from $schema.tab_pool_runtime_assignment
                        where pool_id = $1 and runtime_id = $2
                        order by id desc
                        limit 1
                        """,
                List.of(poolId, runtimeId),
                "Pool runtime assignment",
                poolRuntimeAssignmentModelMapper::fromRow);
    }
}
