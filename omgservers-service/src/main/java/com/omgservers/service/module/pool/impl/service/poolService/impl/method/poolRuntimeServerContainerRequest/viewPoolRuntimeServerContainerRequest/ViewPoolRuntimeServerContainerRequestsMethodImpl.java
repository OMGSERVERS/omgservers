package com.omgservers.service.module.pool.impl.service.poolService.impl.method.poolRuntimeServerContainerRequest.viewPoolRuntimeServerContainerRequest;

import com.omgservers.model.dto.pool.poolRuntimeServerContainerRequest.ViewPoolRuntimeServerContainerRequestsRequest;
import com.omgservers.model.dto.pool.poolRuntimeServerContainerRequest.ViewPoolRuntimeServerContainerRequestsResponse;
import com.omgservers.service.module.pool.impl.operation.poolRuntimeServerContainerRequest.selectActivePoolRuntimeServerContainerRequestsByPoolId.SelectActivePoolRuntimeServerContainerRequestsByPoolIdOperation;
import com.omgservers.service.operation.checkShard.CheckShardOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class ViewPoolRuntimeServerContainerRequestsMethodImpl implements ViewPoolRuntimeServerContainerRequestsMethod {

    final SelectActivePoolRuntimeServerContainerRequestsByPoolIdOperation
            selectActivePoolRuntimeServerContainerRequestsByPoolIdOperation;
    final CheckShardOperation checkShardOperation;

    final PgPool pgPool;

    @Override
    public Uni<ViewPoolRuntimeServerContainerRequestsResponse> viewPoolRuntimeServerContainerRequests(
            final ViewPoolRuntimeServerContainerRequestsRequest request) {
        log.debug("View pool runtime server container request, request={}", request);

        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shard -> {
                    final var poolId = request.getPoolId();
                    return pgPool.withTransaction(
                            sqlConnection -> selectActivePoolRuntimeServerContainerRequestsByPoolIdOperation
                                    .selectActivePoolRuntimeServerContainerRequestsByPoolId(sqlConnection,
                                            shard.shard(),
                                            poolId));
                })
                .map(ViewPoolRuntimeServerContainerRequestsResponse::new);
    }
}
