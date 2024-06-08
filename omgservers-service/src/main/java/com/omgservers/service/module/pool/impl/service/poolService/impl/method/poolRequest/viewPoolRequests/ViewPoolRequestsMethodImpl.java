package com.omgservers.service.module.pool.impl.service.poolService.impl.method.poolRequest.viewPoolRequests;

import com.omgservers.model.dto.pool.poolRequest.ViewPoolRequestsRequest;
import com.omgservers.model.dto.pool.poolRequest.ViewPoolRequestsResponse;
import com.omgservers.service.module.pool.impl.operation.poolRequest.selectActivePoolRequestsByPoolId.SelectActivePoolRequestsByPoolIdOperation;
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
    public Uni<ViewPoolRequestsResponse> viewPoolRequests(
            final ViewPoolRequestsRequest request) {
        log.debug("View pool requests, request={}", request);

        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shard -> {
                    final var poolId = request.getPoolId();
                    return pgPool.withTransaction(
                            sqlConnection -> selectActivePoolRequestsByPoolIdOperation
                                    .selectActivePoolRequestsByPoolId(sqlConnection,
                                            shard.shard(),
                                            poolId));
                })
                .map(ViewPoolRequestsResponse::new);
    }
}
