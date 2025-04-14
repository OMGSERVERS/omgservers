package com.omgservers.service.shard.pool.impl.service.poolService.impl.method.poolCommand;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.shard.pool.poolCommand.DeletePoolCommandRequest;
import com.omgservers.schema.shard.pool.poolCommand.DeletePoolCommandResponse;
import com.omgservers.service.operation.server.ChangeContext;
import com.omgservers.service.operation.server.ChangeWithContextOperation;
import com.omgservers.service.shard.pool.impl.operation.poolCommand.DeletePoolCommandOperation;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = lombok.AccessLevel.PACKAGE)
class DeletePoolCommandMethodImpl implements DeletePoolCommandMethod {

    final DeletePoolCommandOperation deletePoolCommandOperation;
    final ChangeWithContextOperation changeWithContextOperation;

    @Override
    public Uni<DeletePoolCommandResponse> execute(final ShardModel shardModel,
                                                  final DeletePoolCommandRequest request) {
        log.trace("{}", request);

        final var poolId = request.getPoolId();
        final var id = request.getId();
        return changeWithContextOperation.<Boolean>changeWithContext(
                        (changeContext, sqlConnection) -> deletePoolCommandOperation
                                .execute(changeContext,
                                        sqlConnection,
                                        shardModel.shard(),
                                        poolId,
                                        id))
                .map(ChangeContext::getResult)
                .map(DeletePoolCommandResponse::new);
    }
}
