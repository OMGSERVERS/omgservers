package com.omgservers.service.module.pool.impl.service.poolService.impl.method.poolRuntimeAssignment.findPoolRuntimeAssignment;

import com.omgservers.model.dto.pool.poolRuntimeAssignment.FindPoolRuntimeAssignmentRequest;
import com.omgservers.model.dto.pool.poolRuntimeAssignment.FindPoolRuntimeAssignmentResponse;
import com.omgservers.service.module.pool.impl.operation.poolRuntimeAssignment.selectPoolRuntimeAssignmentByPoolIdAndRuntimeId.SelectPoolRuntimeAssignmentByPoolIdAndRuntimeIdOperation;
import com.omgservers.service.operation.checkShard.CheckShardOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class FindPoolRuntimeAssignmentMethodImpl implements FindPoolRuntimeAssignmentMethod {

    final SelectPoolRuntimeAssignmentByPoolIdAndRuntimeIdOperation
            selectPoolRuntimeAssignmentByPoolIdAndRuntimeIdOperation;
    final CheckShardOperation checkShardOperation;

    final PgPool pgPool;

    @Override
    public Uni<FindPoolRuntimeAssignmentResponse> findPoolRuntimeAssignment(
            final FindPoolRuntimeAssignmentRequest request) {
        log.debug("Find pool runtime assignment, request={}", request);

        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shard -> {
                    final var lobbyId = request.getPoolId();
                    final var runtimeId = request.getRuntimeId();
                    return pgPool.withTransaction(sqlConnection ->
                            selectPoolRuntimeAssignmentByPoolIdAndRuntimeIdOperation
                                    .selectPoolRuntimeAssignmentByPoolIdAndRuntimeId(sqlConnection,
                                            shard.shard(),
                                            lobbyId,
                                            runtimeId));
                })
                .map(FindPoolRuntimeAssignmentResponse::new);
    }
}
