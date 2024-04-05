package com.omgservers.service.module.pool.impl.service.poolService.impl.method.poolServer.syncPoolServer;

import com.omgservers.model.dto.pool.poolServer.SyncPoolServerRequest;
import com.omgservers.model.dto.pool.poolServer.SyncPoolServerResponse;
import com.omgservers.service.exception.ExceptionQualifierEnum;
import com.omgservers.service.exception.ServerSideNotFoundException;
import com.omgservers.service.module.pool.impl.operation.pool.hasPool.HasPoolOperation;
import com.omgservers.service.module.pool.impl.operation.poolServer.upsertPoolServer.UpsertPoolServerOperation;
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
class SyncPoolServerMethodImpl implements SyncPoolServerMethod {

    final UpsertPoolServerOperation upsertPoolServerOperation;
    final ChangeWithContextOperation changeWithContextOperation;
    final CheckShardOperation checkShardOperation;
    final HasPoolOperation hasPoolOperation;

    @Override
    public Uni<SyncPoolServerResponse> syncPoolServer(final SyncPoolServerRequest request) {
        log.debug("Sync pool server, request={}", request);

        final var shardKey = request.getRequestShardKey();
        final var poolServer = request.getPoolServer();
        final var poolId = poolServer.getPoolId();

        return Uni.createFrom().voidItem()
                .flatMap(voidItem -> checkShardOperation.checkShard(shardKey))
                .flatMap(shardModel -> {
                    final var shard = shardModel.shard();
                    return changeWithContextOperation.<Boolean>changeWithContext(
                                    (context, sqlConnection) -> hasPoolOperation
                                            .hasPool(sqlConnection, shard, poolId)
                                            .flatMap(has -> {
                                                if (has) {
                                                    return upsertPoolServerOperation.upsertPoolServer(context,
                                                            sqlConnection,
                                                            shard,
                                                            poolServer);
                                                } else {
                                                    throw new ServerSideNotFoundException(
                                                            ExceptionQualifierEnum.PARENT_NOT_FOUND,
                                                            "pool does not exist or was deleted, id=" + poolId);
                                                }
                                            }))
                            .map(ChangeContext::getResult);
                })
                .map(SyncPoolServerResponse::new);
    }
}
