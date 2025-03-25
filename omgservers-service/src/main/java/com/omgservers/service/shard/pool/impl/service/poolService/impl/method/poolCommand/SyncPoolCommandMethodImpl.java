package com.omgservers.service.shard.pool.impl.service.poolService.impl.method.poolCommand;

import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
import com.omgservers.schema.module.pool.poolCommand.SyncPoolCommandRequest;
import com.omgservers.schema.module.pool.poolCommand.SyncPoolCommandResponse;
import com.omgservers.service.exception.ServerSideNotFoundException;
import com.omgservers.service.operation.server.ChangeContext;
import com.omgservers.service.operation.server.ChangeWithContextOperation;
import com.omgservers.service.operation.server.CheckShardOperation;
import com.omgservers.service.shard.pool.impl.operation.pool.VerifyPoolExistsOperation;
import com.omgservers.service.shard.pool.impl.operation.poolCommand.UpsertPoolCommandOperation;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class SyncPoolCommandMethodImpl implements SyncPoolCommandMethod {

    final UpsertPoolCommandOperation upsertPoolCommandOperation;
    final ChangeWithContextOperation changeWithContextOperation;
    final CheckShardOperation checkShardOperation;
    final VerifyPoolExistsOperation verifyPoolExistsOperation;

    @Override
    public Uni<SyncPoolCommandResponse> execute(final SyncPoolCommandRequest request) {
        log.trace("{}", request);

        final var shardKey = request.getRequestShardKey();
        final var poolCommand = request.getPoolCommand();
        final var poolId = poolCommand.getPoolId();

        return checkShardOperation.checkShard(shardKey)
                .flatMap(shardModel -> {
                    final var shard = shardModel.shard();
                    return changeWithContextOperation.<Boolean>changeWithContext(
                                    (context, sqlConnection) -> verifyPoolExistsOperation
                                            .execute(sqlConnection, shard, poolId)
                                            .flatMap(has -> {
                                                if (has) {
                                                    return upsertPoolCommandOperation.execute(
                                                            context,
                                                            sqlConnection,
                                                            shard,
                                                            poolCommand);
                                                } else {
                                                    throw new ServerSideNotFoundException(
                                                            ExceptionQualifierEnum.PARENT_NOT_FOUND,
                                                            "pool does not exist or was deleted, id=" + poolId);
                                                }
                                            }))
                            .map(ChangeContext::getResult);
                })
                .map(SyncPoolCommandResponse::new);
    }
}
