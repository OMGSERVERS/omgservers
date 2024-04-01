package com.omgservers.service.module.pool.impl.service.poolService.impl.method.poolRuntimeServerContainerRef.syncPoolRuntimeServerContainerRef;

import com.omgservers.model.dto.pool.poolRuntimeServerContainerRef.SyncPoolRuntimeServerContainerRefRequest;
import com.omgservers.model.dto.pool.poolRuntimeServerContainerRef.SyncPoolRuntimeServerContainerRefResponse;
import com.omgservers.service.exception.ExceptionQualifierEnum;
import com.omgservers.service.exception.ServerSideNotFoundException;
import com.omgservers.service.module.pool.impl.operation.pool.hasPool.HasPoolOperation;
import com.omgservers.service.module.pool.impl.operation.poolRuntimeServerContainerRef.upsertPoolRuntimeServerContainerRef.UpsertPoolRuntimeServerContainerRefOperation;
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
class SyncPoolRuntimeServerContainerRefMethodImpl implements SyncPoolRuntimeServerContainerRefMethod {

    final UpsertPoolRuntimeServerContainerRefOperation upsertPoolRuntimeServerContainerRefOperation;
    final ChangeWithContextOperation changeWithContextOperation;
    final CheckShardOperation checkShardOperation;
    final HasPoolOperation hasPoolOperation;

    @Override
    public Uni<SyncPoolRuntimeServerContainerRefResponse> syncPoolRuntimeServerContainerRef(
            final SyncPoolRuntimeServerContainerRefRequest request) {
        log.debug("Sync pool runtime server container ref, request={}", request);

        final var shardKey = request.getRequestShardKey();
        final var poolRuntimeServerContainerRef = request.getPoolRuntimeServerContainerRef();
        final var poolId = poolRuntimeServerContainerRef.getPoolId();

        return checkShardOperation.checkShard(shardKey)
                .flatMap(shardModel -> {
                    final var shard = shardModel.shard();
                    return changeWithContextOperation.<Boolean>changeWithContext(
                                    (context, sqlConnection) -> hasPoolOperation
                                            .hasPool(sqlConnection, shard, poolId)
                                            .flatMap(has -> {
                                                if (has) {
                                                    return upsertPoolRuntimeServerContainerRefOperation
                                                            .upsertPoolRuntimeServerContainerRef(
                                                                    context,
                                                                    sqlConnection,
                                                                    shard,
                                                                    poolRuntimeServerContainerRef);
                                                } else {
                                                    throw new ServerSideNotFoundException(
                                                            ExceptionQualifierEnum.PARENT_NOT_FOUND,
                                                            "pool does not exist or was deleted, id=" + poolId);
                                                }
                                            }))
                            .map(ChangeContext::getResult);
                })
                .map(SyncPoolRuntimeServerContainerRefResponse::new);
    }
}
