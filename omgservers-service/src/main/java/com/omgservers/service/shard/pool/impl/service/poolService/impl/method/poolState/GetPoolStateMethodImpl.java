package com.omgservers.service.shard.pool.impl.service.poolService.impl.method.poolState;

import com.omgservers.schema.model.poolState.PoolStateDto;
import com.omgservers.schema.module.pool.poolState.GetPoolStateRequest;
import com.omgservers.schema.module.pool.poolState.GetPoolStateResponse;
import com.omgservers.service.shard.pool.impl.operation.pool.SelectPoolOperation;
import com.omgservers.service.shard.pool.impl.operation.poolContainer.SelectActivePoolContainersByPoolIdOperation;
import com.omgservers.service.shard.pool.impl.operation.poolRequest.SelectActivePoolRequestsByPoolIdOperation;
import com.omgservers.service.shard.pool.impl.operation.poolServer.SelectActivePoolServersByPoolIdOperation;
import com.omgservers.service.operation.server.CheckShardOperation;
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
    final SelectActivePoolRequestsByPoolIdOperation selectActivePoolRequestsByPoolIdOperation;
    final SelectActivePoolServersByPoolIdOperation selectActivePoolServersByPoolIdOperation;
    final SelectPoolOperation selectPoolOperation;

    final CheckShardOperation checkShardOperation;

    final PgPool pgPool;

    @Override
    public Uni<GetPoolStateResponse> execute(final GetPoolStateRequest request) {
        log.trace("{}", request);

        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shardModel -> {
                    final var shard = shardModel.shard();
                    final var poolId = request.getPoolId();
                    return pgPool.withTransaction(sqlConnection -> selectPoolOperation
                            .execute(sqlConnection,
                                    shard,
                                    poolId)
                            .flatMap(pool -> selectActivePoolServersByPoolIdOperation
                                    .execute(sqlConnection,
                                            shard,
                                            poolId)
                                    .flatMap(poolServers -> selectActivePoolContainersByPoolIdOperation
                                            .execute(sqlConnection,
                                                    shard,
                                                    poolId)
                                            .flatMap(
                                                    poolContainers -> selectActivePoolRequestsByPoolIdOperation
                                                            .execute(sqlConnection,
                                                                    shard,
                                                                    poolId)
                                                            .map(poolRequests -> new PoolStateDto(
                                                                    pool, poolServers,
                                                                    poolContainers,
                                                                    poolRequests)))))
                    );
                })
                .map(GetPoolStateResponse::new);
    }
}
