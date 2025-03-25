package com.omgservers.service.shard.pool.impl.service.poolService.impl.method.poolCommand;

import com.omgservers.schema.module.pool.poolCommand.ViewPoolCommandRequest;
import com.omgservers.schema.module.pool.poolCommand.ViewPoolCommandResponse;
import com.omgservers.service.operation.server.CheckShardOperation;
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
    final CheckShardOperation checkShardOperation;

    final PgPool pgPool;

    @Override
    public Uni<ViewPoolCommandResponse> execute(final ViewPoolCommandRequest request) {
        log.trace("{}", request);

        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shard -> {
                    final var poolId = request.getPoolId();
                    return pgPool.withTransaction(
                            sqlConnection -> selectActivePoolCommandsByPoolIdOperation
                                    .execute(sqlConnection,
                                            shard.shard(),
                                            poolId));
                })
                .map(ViewPoolCommandResponse::new);
    }
}
