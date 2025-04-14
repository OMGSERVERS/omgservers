package com.omgservers.service.shard.pool.impl.service.poolService.impl.method.poolCommand;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.shard.pool.poolCommand.ViewPoolCommandRequest;
import com.omgservers.schema.shard.pool.poolCommand.ViewPoolCommandResponse;
import com.omgservers.service.shard.pool.impl.operation.poolCommand.SelectActivePoolCommandsByPoolIdOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class ViewPoolCommandsMethodImpl implements ViewPoolCommandsMethod {

    final SelectActivePoolCommandsByPoolIdOperation selectActivePoolCommandsByPoolIdOperation;

    final PgPool pgPool;

    @Override
    public Uni<ViewPoolCommandResponse> execute(final ShardModel shardModel,
                                                final ViewPoolCommandRequest request) {
        log.trace("{}", request);

        final var poolId = request.getPoolId();
        return pgPool.withTransaction(sqlConnection -> selectActivePoolCommandsByPoolIdOperation.execute(sqlConnection,
                        shardModel.shard(),
                        poolId))
                .map(ViewPoolCommandResponse::new);
    }
}
