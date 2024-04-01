package com.omgservers.service.module.pool.impl.service.poolService.impl.method.poolRuntimeAssignment.getPoolRuntimeAssignment;

import com.omgservers.model.dto.pool.poolRuntimeAssignment.GetPoolRuntimeAssignmentRequest;
import com.omgservers.model.dto.pool.poolRuntimeAssignment.GetPoolRuntimeAssignmentResponse;
import com.omgservers.service.module.pool.impl.operation.poolRuntimeAssignment.selectPoolRuntimeAssignment.SelectPoolRuntimeAssignmentOperation;
import com.omgservers.service.operation.checkShard.CheckShardOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class GetPoolRuntimeAssignmentMethodImpl implements GetPoolRuntimeAssignmentMethod {

    final SelectPoolRuntimeAssignmentOperation selectPoolRuntimeAssignmentOperation;
    final CheckShardOperation checkShardOperation;

    final PgPool pgPool;

    @Override
    public Uni<GetPoolRuntimeAssignmentResponse> getPoolRuntimeAssignment(
            final GetPoolRuntimeAssignmentRequest request) {
        log.debug("Get pool runtime assignment, request={}", request);

        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shard -> {
                    final var poolId = request.getPoolId();
                    final var id = request.getId();
                    return pgPool.withTransaction(sqlConnection -> selectPoolRuntimeAssignmentOperation
                            .selectPoolRuntimeAssignment(sqlConnection, shard.shard(), poolId, id));
                })
                .map(GetPoolRuntimeAssignmentResponse::new);
    }
}
