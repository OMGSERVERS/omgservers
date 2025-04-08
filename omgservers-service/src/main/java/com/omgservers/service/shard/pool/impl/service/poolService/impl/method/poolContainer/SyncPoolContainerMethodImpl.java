package com.omgservers.service.shard.pool.impl.service.poolService.impl.method.poolContainer;

import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.module.pool.poolContainer.SyncPoolContainerRequest;
import com.omgservers.schema.module.pool.poolContainer.SyncPoolContainerResponse;
import com.omgservers.service.exception.ServerSideNotFoundException;
import com.omgservers.service.operation.server.ChangeContext;
import com.omgservers.service.operation.server.ChangeWithContextOperation;
import com.omgservers.service.shard.pool.impl.operation.poolContainer.UpsertPoolContainerOperation;
import com.omgservers.service.shard.pool.impl.operation.poolServer.VerifyPoolServerExistsOperation;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class SyncPoolContainerMethodImpl implements SyncPoolContainerMethod {

    final VerifyPoolServerExistsOperation verifyPoolServerExistsOperation;
    final UpsertPoolContainerOperation upsertPoolContainerOperation;
    final ChangeWithContextOperation changeWithContextOperation;

    @Override
    public Uni<SyncPoolContainerResponse> execute(final ShardModel shardModel,
                                                  final SyncPoolContainerRequest request) {
        log.trace("{}", request);

        final var shardKey = request.getRequestShardKey();
        final var poolContainer = request.getPoolContainer();
        final var poolId = poolContainer.getPoolId();
        final var serverId = poolContainer.getServerId();

        return changeWithContextOperation.<Boolean>changeWithContext((context, sqlConnection) ->
                        verifyPoolServerExistsOperation.execute(sqlConnection, shardModel.shard(), poolId, serverId)
                                .flatMap(exists -> {
                                    if (exists) {
                                        return upsertPoolContainerOperation.execute(
                                                context,
                                                sqlConnection,
                                                shardModel.shard(),
                                                poolContainer);
                                    } else {
                                        throw new ServerSideNotFoundException(
                                                ExceptionQualifierEnum.PARENT_NOT_FOUND,
                                                "pool server does not exist or was deleted, " +
                                                        "serverId=" + serverId);
                                    }
                                }))
                .map(ChangeContext::getResult)
                .map(SyncPoolContainerResponse::new);
    }
}
