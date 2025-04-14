package com.omgservers.service.shard.pool.impl.service.poolService.impl.method.poolServer;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.shard.pool.poolServer.ViewPoolServersRequest;
import com.omgservers.schema.shard.pool.poolServer.ViewPoolServersResponse;
import com.omgservers.service.shard.pool.impl.operation.poolServer.SelectActivePoolServersByPoolIdOperation;
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

    final PgPool pgPool;

    @Override
    public Uni<ViewPoolServersResponse> execute(final ShardModel shardModel,
                                                final ViewPoolServersRequest request) {
        log.trace("{}", request);

        final var poolId = request.getPoolId();
        return pgPool.withTransaction(sqlConnection -> selectActivePoolServersByPoolIdOperation
                        .execute(sqlConnection,
                                shardModel.shard(),
                                poolId))
                .map(ViewPoolServersResponse::new);
    }
}
