package com.omgservers.service.module.pool.impl.service.poolService.impl.method.poolRuntimeServerContainerRequest.syncPoolRuntimeServerContainerRequest;

import com.omgservers.model.dto.pool.poolRuntimeServerContainerRequest.SyncPoolRuntimeServerContainerRequestRequest;
import com.omgservers.model.dto.pool.poolRuntimeServerContainerRequest.SyncPoolRuntimeServerContainerRequestResponse;
import com.omgservers.service.exception.ExceptionQualifierEnum;
import com.omgservers.service.exception.ServerSideNotFoundException;
import com.omgservers.service.module.pool.impl.operation.pool.hasPool.HasPoolOperation;
import com.omgservers.service.module.pool.impl.operation.poolRuntimeServerContainerRequest.upsertPoolRuntimeServerContainerRequest.UpsertPoolRuntimeServerContainerRequestOperation;
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
class SyncPoolRuntimeServerContainerRequestMethodImpl implements SyncPoolRuntimeServerContainerRequestMethod {

    final UpsertPoolRuntimeServerContainerRequestOperation upsertPoolRuntimeServerContainerRequestOperation;
    final ChangeWithContextOperation changeWithContextOperation;
    final CheckShardOperation checkShardOperation;
    final HasPoolOperation hasPoolOperation;

    @Override
    public Uni<SyncPoolRuntimeServerContainerRequestResponse> syncPoolRuntimeServerContainerRequest(
            final SyncPoolRuntimeServerContainerRequestRequest request) {
        log.debug("Sync pool runtime server container request, request={}", request);

        final var shardKey = request.getRequestShardKey();
        final var poolRuntimeServerContainerRequest = request.getPoolRuntimeServerContainerRequest();
        final var poolId = poolRuntimeServerContainerRequest.getPoolId();

        return Uni.createFrom().voidItem()
                .flatMap(voidItem -> checkShardOperation.checkShard(shardKey))
                .flatMap(shardModel -> {
                    final var shard = shardModel.shard();
                    return changeWithContextOperation.<Boolean>changeWithContext(
                                    (context, sqlConnection) -> hasPoolOperation
                                            .hasPool(sqlConnection, shard, poolId)
                                            .flatMap(has -> {
                                                if (has) {
                                                    return upsertPoolRuntimeServerContainerRequestOperation
                                                            .upsertPoolRuntimeServerContainerRequest(
                                                                    context,
                                                                    sqlConnection,
                                                                    shard,
                                                                    poolRuntimeServerContainerRequest);
                                                } else {
                                                    throw new ServerSideNotFoundException(
                                                            ExceptionQualifierEnum.PARENT_NOT_FOUND,
                                                            "pool does not exist or was deleted, id=" + poolId);
                                                }
                                            }))
                            .map(ChangeContext::getResult);
                })
                .map(SyncPoolRuntimeServerContainerRequestResponse::new);
    }
}
