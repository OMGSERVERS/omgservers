package com.omgservers.service.module.pool.impl.service.poolService.impl.method.poolRuntimeServerContainerRequest.getPoolRuntimeServerContainerRequest;

import com.omgservers.model.dto.pool.poolRuntimeServerContainerRequest.GetPoolRuntimeServerContainerRequestRequest;
import com.omgservers.model.dto.pool.poolRuntimeServerContainerRequest.GetPoolRuntimeServerContainerRequestResponse;
import com.omgservers.service.module.pool.impl.operation.poolRuntimeServerContainerRequest.selectPoolRuntimeServerContainerRequest.SelectPoolRuntimeServerContainerRequestOperation;
import com.omgservers.service.operation.checkShard.CheckShardOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class GetPoolRuntimeServerContainerRequestMethodImpl implements GetPoolRuntimeServerContainerRequestMethod {

    final SelectPoolRuntimeServerContainerRequestOperation selectPoolRuntimeServerContainerRequestOperation;
    final CheckShardOperation checkShardOperation;

    final PgPool pgPool;

    @Override
    public Uni<GetPoolRuntimeServerContainerRequestResponse> getPoolRuntimeServerContainerRequest(
            final GetPoolRuntimeServerContainerRequestRequest request) {
        log.debug("Get pool runtime server container request, request={}", request);

        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shard -> {
                    final var poolId = request.getPoolId();
                    final var id = request.getId();
                    return pgPool.withTransaction(sqlConnection -> selectPoolRuntimeServerContainerRequestOperation
                            .selectPoolRuntimeServerContainerRequest(sqlConnection, shard.shard(), poolId, id));
                })
                .map(GetPoolRuntimeServerContainerRequestResponse::new);
    }
}
