package com.omgservers.service.shard.pool.impl.service.poolService.impl.method.poolRequest;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.shard.pool.poolRequest.ViewPoolRequestsRequest;
import com.omgservers.schema.shard.pool.poolRequest.ViewPoolRequestsResponse;
import com.omgservers.service.shard.pool.impl.operation.poolRequest.SelectActivePoolRequestsByPoolIdOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class ViewPoolRequestsMethodImpl implements ViewPoolRequestsMethod {

    final SelectActivePoolRequestsByPoolIdOperation selectActivePoolRequestsByPoolIdOperation;

    final PgPool pgPool;

    @Override
    public Uni<ViewPoolRequestsResponse> execute(final ShardModel shardModel,
                                                 final ViewPoolRequestsRequest request) {
        log.trace("{}", request);

        final var poolId = request.getPoolId();
        return pgPool.withTransaction(sqlConnection -> selectActivePoolRequestsByPoolIdOperation
                        .execute(sqlConnection,
                                shardModel.shard(),
                                poolId))
                .map(ViewPoolRequestsResponse::new);
    }
}
