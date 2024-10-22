package com.omgservers.service.module.pool.impl.service.poolService.impl.method.poolServer.viewPoolServers;

import com.omgservers.schema.module.pool.poolServer.ViewPoolServersRequest;
import com.omgservers.schema.module.pool.poolServer.ViewPoolServerResponse;
import com.omgservers.service.module.pool.impl.operation.poolServer.selectActivePoolServersByPoolId.SelectActivePoolServersByPoolIdOperation;
import com.omgservers.service.operation.checkShard.CheckShardOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class ViewPoolServersMethodImpl implements ViewPoolServersMethod {

    final SelectActivePoolServersByPoolIdOperation selectActivePoolServersByPoolIdOperation;
    final CheckShardOperation checkShardOperation;

    final PgPool pgPool;

    @Override
    public Uni<ViewPoolServerResponse> viewPoolServers(final ViewPoolServersRequest request) {
        log.debug("Requested, {}", request);

        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shard -> {
                    final var poolId = request.getPoolId();
                    return pgPool.withTransaction(sqlConnection -> selectActivePoolServersByPoolIdOperation
                            .selectActivePoolServersByPoolId(sqlConnection,
                                    shard.shard(),
                                    poolId));
                })
                .map(ViewPoolServerResponse::new);
    }
}
