package com.omgservers.service.module.pool.impl.service.poolService.impl.method.poolRequest;

import com.omgservers.schema.module.pool.poolRequest.ViewPoolRequestsRequest;
import com.omgservers.schema.module.pool.poolRequest.ViewPoolRequestsResponse;
import com.omgservers.service.module.pool.impl.operation.poolRequest.SelectActivePoolRequestsByPoolIdOperation;
import com.omgservers.service.operation.checkShard.CheckShardOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class ViewPoolRequestsMethodImpl implements ViewPoolRequestsMethod {

    final SelectActivePoolRequestsByPoolIdOperation
            selectActivePoolRequestsByPoolIdOperation;
    final CheckShardOperation checkShardOperation;

    final PgPool pgPool;

    @Override
    public Uni<ViewPoolRequestsResponse> execute(
            final ViewPoolRequestsRequest request) {
        log.debug("Requested, {}", request);

        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shard -> {
                    final var poolId = request.getPoolId();
                    return pgPool.withTransaction(
                            sqlConnection -> selectActivePoolRequestsByPoolIdOperation
                                    .execute(sqlConnection,
                                            shard.shard(),
                                            poolId));
                })
                .map(ViewPoolRequestsResponse::new);
    }
}
