package com.omgservers.service.module.pool.impl.service.poolService.impl.method.poolServerContainer.syncPoolServerContainer;

import com.omgservers.schema.module.pool.poolServerContainer.SyncPoolServerContainerRequest;
import com.omgservers.schema.module.pool.poolServerContainer.SyncPoolServerContainerResponse;
import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
import com.omgservers.service.exception.ServerSideNotFoundException;
import com.omgservers.service.module.pool.impl.operation.poolServer.hasPoolServer.HasPoolServerOperation;
import com.omgservers.service.module.pool.impl.operation.poolServerContainer.upsertPoolServerContainer.UpsertPoolServerContainerOperation;
import com.omgservers.service.server.operation.changeWithContext.ChangeContext;
import com.omgservers.service.server.operation.changeWithContext.ChangeWithContextOperation;
import com.omgservers.service.server.operation.checkShard.CheckShardOperation;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class SyncPoolServerContainerMethodImpl implements SyncPoolServerContainerMethod {

    final UpsertPoolServerContainerOperation upsertPoolServerContainerOperation;
    final ChangeWithContextOperation changeWithContextOperation;
    final HasPoolServerOperation hasPoolServerOperation;
    final CheckShardOperation checkShardOperation;

    @Override
    public Uni<SyncPoolServerContainerResponse> syncPoolServerContainer(
            final SyncPoolServerContainerRequest request) {
        log.debug("Sync pool server container, request={}", request);

        final var shardKey = request.getRequestShardKey();
        final var poolServerContainer = request.getPoolServerContainer();
        final var poolId = poolServerContainer.getPoolId();
        final var serverId = poolServerContainer.getServerId();

        return checkShardOperation.checkShard(shardKey)
                .flatMap(shardModel -> {
                    final var shard = shardModel.shard();
                    return changeWithContextOperation.<Boolean>changeWithContext(
                                    (context, sqlConnection) -> hasPoolServerOperation
                                            .hasPoolServer(sqlConnection, shard, poolId, serverId)
                                            .flatMap(has -> {
                                                if (has) {
                                                    return upsertPoolServerContainerOperation
                                                            .upsertPoolServerContainer(
                                                                    context,
                                                                    sqlConnection,
                                                                    shard,
                                                                    poolServerContainer);
                                                } else {
                                                    throw new ServerSideNotFoundException(
                                                            ExceptionQualifierEnum.PARENT_NOT_FOUND,
                                                            "pool server does not exist or was deleted, " +
                                                                    "serverId=" + serverId);
                                                }
                                            }))
                            .map(ChangeContext::getResult);
                })
                .map(SyncPoolServerContainerResponse::new);
    }
}
