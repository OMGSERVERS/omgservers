package com.omgservers.service.shard.pool.impl.service.poolService.impl.method.poolCommand;

import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.shard.pool.poolCommand.SyncPoolCommandRequest;
import com.omgservers.schema.shard.pool.poolCommand.SyncPoolCommandResponse;
import com.omgservers.service.exception.ServerSideNotFoundException;
import com.omgservers.service.operation.server.ChangeContext;
import com.omgservers.service.operation.server.ChangeWithContextOperation;
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
    final VerifyPoolExistsOperation verifyPoolExistsOperation;

    @Override
    public Uni<SyncPoolCommandResponse> execute(final ShardModel shardModel,
                                                final SyncPoolCommandRequest request) {
        log.trace("{}", request);

        final var poolCommand = request.getPoolCommand();
        final var poolId = poolCommand.getPoolId();

        return changeWithContextOperation.<Boolean>changeWithContext(
                        (context, sqlConnection) -> verifyPoolExistsOperation
                                .execute(sqlConnection, shardModel.shard(), poolId)
                                .flatMap(exists -> {
                                    if (exists) {
                                        return upsertPoolCommandOperation.execute(
                                                context,
                                                sqlConnection,
                                                shardModel.shard(),
                                                poolCommand);
                                    } else {
                                        throw new ServerSideNotFoundException(
                                                ExceptionQualifierEnum.PARENT_NOT_FOUND,
                                                "pool does not exist or was deleted, id=" + poolId);
                                    }
                                }))
                .map(ChangeContext::getResult)
                .map(SyncPoolCommandResponse::new);
    }
}
