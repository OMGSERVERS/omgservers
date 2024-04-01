package com.omgservers.service.module.pool.impl.operation.poolRuntimeAssignment.selectActivePoolRuntimeAssignmentsByPoolId;

import com.omgservers.model.poolRuntimeAssignment.PoolRuntimeAssignmentModel;
import com.omgservers.service.module.pool.impl.mappers.PoolRuntimeAssignmentModelMapper;
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
class SelectActivePoolRuntimeAssignmentsByPoolIdOperationImpl
        implements SelectActivePoolRuntimeAssignmentsByPoolIdOperation {

    final SelectListOperation selectListOperation;

    final PoolRuntimeAssignmentModelMapper poolRuntimeAssignmentModelMapper;

    @Override
    public Uni<List<PoolRuntimeAssignmentModel>> selectActivePoolRuntimeAssignmentsByPoolId(
            final SqlConnection sqlConnection,
            final int shard,
            final Long poolId) {
        return selectListOperation.selectList(
                sqlConnection,
                shard,
                """
                        select
                            id, idempotency_key, pool_id, created, modified, runtime_id, server_id, config, deleted
                        from $schema.tab_pool_runtime_assignment
                        where pool_id = $1 and deleted = false
                        order by id asc
                        """,
                List.of(poolId),
                "Pool runtime assignment",
                poolRuntimeAssignmentModelMapper::fromRow);
    }
}
