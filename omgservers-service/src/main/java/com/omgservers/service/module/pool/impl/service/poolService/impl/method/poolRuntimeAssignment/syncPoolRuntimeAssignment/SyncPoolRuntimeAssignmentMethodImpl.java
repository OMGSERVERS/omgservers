package com.omgservers.service.module.pool.impl.service.poolService.impl.method.poolRuntimeAssignment.syncPoolRuntimeAssignment;

import com.omgservers.model.dto.pool.poolRuntimeAssignment.SyncPoolRuntimeAssignmentRequest;
import com.omgservers.model.dto.pool.poolRuntimeAssignment.SyncPoolRuntimeAssignmentResponse;
import com.omgservers.service.exception.ExceptionQualifierEnum;
import com.omgservers.service.exception.ServerSideNotFoundException;
import com.omgservers.service.module.pool.impl.operation.pool.hasPool.HasPoolOperation;
import com.omgservers.service.module.pool.impl.operation.poolRuntimeAssignment.upsertPoolRuntimeAssignment.UpsertPoolRuntimeAssignmentOperation;
import com.omgservers.service.operation.changeWithContext.ChangeContext;
import com.omgservers.service.operation.changeWithContext.ChangeWithContextOperation;
import com.omgservers.service.operation.checkShard.CheckShardOperation;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class SyncPoolRuntimeAssignmentMethodImpl implements SyncPoolRuntimeAssignmentMethod {

    final UpsertPoolRuntimeAssignmentOperation upsertPoolRuntimeAssignmentOperation;
    final ChangeWithContextOperation changeWithContextOperation;
    final CheckShardOperation checkShardOperation;
    final HasPoolOperation hasPoolOperation;

    @Override
    public Uni<SyncPoolRuntimeAssignmentResponse> syncPoolRuntimeAssignment(
            final SyncPoolRuntimeAssignmentRequest request) {
        log.debug("Sync pool runtime assignment, request={}", request);

        final var shardKey = request.getRequestShardKey();
        final var poolRuntimeAssignment = request.getPoolRuntimeAssignment();
        final var poolId = poolRuntimeAssignment.getPoolId();

        return Uni.createFrom().voidItem()
                .flatMap(voidItem -> checkShardOperation.checkShard(shardKey))
                .flatMap(shardModel -> {
                    final var shard = shardModel.shard();
                    return changeWithContextOperation.<Boolean>changeWithContext(
                                    (context, sqlConnection) -> hasPoolOperation
                                            .hasPool(sqlConnection, shard, poolId)
                                            .flatMap(has -> {
                                                if (has) {
                                                    return upsertPoolRuntimeAssignmentOperation
                                                            .upsertPoolRuntimeAssignmentRequest(
                                                                    context,
                                                                    sqlConnection,
                                                                    shard,
                                                                    poolRuntimeAssignment);
                                                } else {
                                                    throw new ServerSideNotFoundException(
                                                            ExceptionQualifierEnum.PARENT_NOT_FOUND,
                                                            "pool does not exist or was deleted, id=" + poolId);
                                                }
                                            }))
                            .map(ChangeContext::getResult);
                })
                .map(SyncPoolRuntimeAssignmentResponse::new);
    }
}
