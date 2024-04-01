package com.omgservers.service.module.pool.impl.service.poolService.impl.method.poolRuntimeServerContainerRef.viewPoolRuntimeServerContainerRef;

import com.omgservers.model.dto.pool.poolRuntimeServerContainerRef.ViewPoolRuntimeServerContainerRefRequest;
import com.omgservers.model.dto.pool.poolRuntimeServerContainerRef.ViewPoolRuntimeServerContainerRefResponse;
import com.omgservers.service.module.pool.impl.operation.poolRuntimeServerContainerRef.selectActivePoolRuntimeServerContainerRefsByPoolId.SelectActivePoolRuntimeServerContainerRefsByPoolIdOperation;
import com.omgservers.service.operation.checkShard.CheckShardOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class ViewPoolRuntimeServerContainerRefsMethodImpl implements ViewPoolRuntimeServerContainerRefsMethod {

    final SelectActivePoolRuntimeServerContainerRefsByPoolIdOperation
            selectActivePoolRuntimeServerContainerRefsByPoolIdOperation;
    final CheckShardOperation checkShardOperation;

    final PgPool pgPool;

    @Override
    public Uni<ViewPoolRuntimeServerContainerRefResponse> viewPoolRuntimeServerContainerRefs(
            final ViewPoolRuntimeServerContainerRefRequest request) {
        log.debug("View pool runtime server container ref, request={}", request);

        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shard -> {
                    final var poolId = request.getPoolId();
                    return pgPool.withTransaction(
                            sqlConnection -> selectActivePoolRuntimeServerContainerRefsByPoolIdOperation
                                    .selectActivePoolRuntimeServerContainerRefsByPoolId(sqlConnection,
                                            shard.shard(),
                                            poolId));
                })
                .map(ViewPoolRuntimeServerContainerRefResponse::new);
    }
}
