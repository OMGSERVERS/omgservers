package com.omgservers.service.shard.pool.impl.service.poolService.impl.method.poolServer;

import com.omgservers.schema.module.pool.poolServer.ViewPoolServerResponse;
import com.omgservers.schema.module.pool.poolServer.ViewPoolServersRequest;
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
class ViewPoolServersMethodImpl implements ViewPoolServersMethod {

    final SelectActivePoolServersByPoolIdOperation selectActivePoolServersByPoolIdOperation;
    final CheckShardOperation checkShardOperation;

    final PgPool pgPool;

    @Override
    public Uni<ViewPoolServerResponse> execute(final ViewPoolServersRequest request) {
        log.trace("{}", request);

        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shard -> {
                    final var poolId = request.getPoolId();
                    return pgPool.withTransaction(sqlConnection -> selectActivePoolServersByPoolIdOperation
                            .execute(sqlConnection,
                                    shard.shard(),
                                    poolId));
                })
                .map(ViewPoolServerResponse::new);
    }
}
