package com.omgservers.service.shard.pool.impl.service.poolService.impl.method.poolState;

import com.omgservers.schema.model.poolState.PoolStateDto;
import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.shard.pool.poolState.GetPoolStateRequest;
import com.omgservers.schema.shard.pool.poolState.GetPoolStateResponse;
import com.omgservers.service.shard.pool.impl.operation.pool.SelectPoolOperation;
import com.omgservers.service.shard.pool.impl.operation.poolCommand.SelectActivePoolCommandsByPoolIdOperation;
import com.omgservers.service.shard.pool.impl.operation.poolContainer.SelectActivePoolContainersByPoolIdOperation;
import com.omgservers.service.shard.pool.impl.operation.poolRequest.SelectActivePoolRequestsByPoolIdOperation;
import com.omgservers.service.shard.pool.impl.operation.poolServer.SelectActivePoolServersByPoolIdOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class GetPoolStateMethodImpl implements GetPoolStateMethod {

    final SelectActivePoolContainersByPoolIdOperation selectActivePoolContainersByPoolIdOperation;
    final SelectActivePoolCommandsByPoolIdOperation selectActivePoolCommandsByPoolIdOperation;
    final SelectActivePoolRequestsByPoolIdOperation selectActivePoolRequestsByPoolIdOperation;
    final SelectActivePoolServersByPoolIdOperation selectActivePoolServersByPoolIdOperation;
    final SelectPoolOperation selectPoolOperation;

    final PgPool pgPool;

    @Override
    public Uni<GetPoolStateResponse> execute(final ShardModel shardModel,
                                             final GetPoolStateRequest request) {
        log.trace("{}", request);

        final var shard = shardModel.shard();
        final var poolId = request.getPoolId();
        final var poolState = new PoolStateDto();

        return pgPool.withTransaction(sqlConnection -> selectPoolOperation
                        .execute(sqlConnection, shard, poolId)
                        .invoke(poolState::setPool)
                        .flatMap(pool -> selectActivePoolCommandsByPoolIdOperation
                                .execute(sqlConnection, shard, poolId)
                                .invoke(poolState::setPoolCommands))
                        .flatMap(pool -> selectActivePoolRequestsByPoolIdOperation
                                .execute(sqlConnection, shard, poolId)
                                .invoke(poolState::setPoolRequests))
                        .flatMap(pool -> selectActivePoolServersByPoolIdOperation
                                .execute(sqlConnection, shard, poolId)
                                .invoke(poolState::setPoolServers))
                        .flatMap(poolServers -> selectActivePoolContainersByPoolIdOperation
                                .execute(sqlConnection, shard, poolId)
                                .invoke(poolState::setPoolContainers))
                )
                .replaceWith(poolState)
                .map(GetPoolStateResponse::new);
    }
}
