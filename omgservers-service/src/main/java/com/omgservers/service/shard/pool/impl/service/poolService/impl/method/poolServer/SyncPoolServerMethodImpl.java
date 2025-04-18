package com.omgservers.service.shard.pool.impl.service.poolService.impl.method.poolServer;

import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.shard.pool.poolServer.SyncPoolServerRequest;
import com.omgservers.schema.shard.pool.poolServer.SyncPoolServerResponse;
import com.omgservers.service.exception.ServerSideNotFoundException;
import com.omgservers.service.operation.server.ChangeContext;
import com.omgservers.service.operation.server.ChangeWithContextOperation;
import com.omgservers.service.shard.pool.impl.operation.pool.VerifyPoolExistsOperation;
import com.omgservers.service.shard.pool.impl.operation.poolServer.UpsertPoolServerOperation;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class SyncPoolServerMethodImpl implements SyncPoolServerMethod {

    final ChangeWithContextOperation changeWithContextOperation;
    final UpsertPoolServerOperation upsertPoolServerOperation;
    final VerifyPoolExistsOperation verifyPoolExistsOperation;

    @Override
    public Uni<SyncPoolServerResponse> execute(final ShardModel shardModel,
                                               final SyncPoolServerRequest request) {
        log.trace("{}", request);

        final var poolServer = request.getPoolServer();
        final var poolId = poolServer.getPoolId();

        return changeWithContextOperation.<Boolean>changeWithContext(
                        (context, sqlConnection) -> verifyPoolExistsOperation
                                .execute(sqlConnection, shardModel.slot(), poolId)
                                .flatMap(exists -> {
                                    if (exists) {
                                        return upsertPoolServerOperation.execute(context,
                                                sqlConnection,
                                                shardModel.slot(),
                                                poolServer);
                                    } else {
                                        throw new ServerSideNotFoundException(
                                                ExceptionQualifierEnum.PARENT_NOT_FOUND,
                                                "pool does not exist or was deleted, id=" + poolId);
                                    }
                                }))
                .map(ChangeContext::getResult)
                .map(SyncPoolServerResponse::new);
    }
}
