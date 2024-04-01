package com.omgservers.service.module.pool.impl.service.poolService.impl.method.poolRuntimeAssignment.viewPoolRuntimeAssignment;

import com.omgservers.model.dto.pool.poolRuntimeAssignment.ViewPoolRuntimeAssignmentsRequest;
import com.omgservers.model.dto.pool.poolRuntimeAssignment.ViewPoolRuntimeAssignmentsResponse;
import com.omgservers.service.module.pool.impl.operation.poolRuntimeAssignment.selectActivePoolRuntimeAssignmentsByPoolId.SelectActivePoolRuntimeAssignmentsByPoolIdOperation;
import com.omgservers.service.operation.checkShard.CheckShardOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class ViewPoolRuntimeAssignmentsMethodImpl implements ViewPoolRuntimeAssignmentsMethod {

    final SelectActivePoolRuntimeAssignmentsByPoolIdOperation selectActivePoolRuntimeAssignmentsByPoolIdOperation;
    final CheckShardOperation checkShardOperation;

    final PgPool pgPool;

    @Override
    public Uni<ViewPoolRuntimeAssignmentsResponse> viewPoolRuntimeAssignments(
            final ViewPoolRuntimeAssignmentsRequest request) {
        log.debug("View pool runtime assignment, request={}", request);

        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shard -> {
                    final var poolId = request.getPoolId();
                    return pgPool.withTransaction(sqlConnection -> selectActivePoolRuntimeAssignmentsByPoolIdOperation
                            .selectActivePoolRuntimeAssignmentsByPoolId(sqlConnection,
                                    shard.shard(),
                                    poolId));
                })
                .map(ViewPoolRuntimeAssignmentsResponse::new);
    }
}
