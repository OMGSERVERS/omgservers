package com.omgservers.service.module.pool.impl.service.poolService.impl.method.poolRuntimeServerContainerRef.getPoolRuntimeServerContainerRef;

import com.omgservers.model.dto.pool.poolRuntimeServerContainerRef.GetPoolRuntimeServerContainerRefRequest;
import com.omgservers.model.dto.pool.poolRuntimeServerContainerRef.GetPoolRuntimeServerContainerRefResponse;
import com.omgservers.service.module.pool.impl.operation.poolRuntimeServerContainerRef.selectPoolRuntimeServerContainerRef.SelectPoolRuntimeServerContainerRefOperation;
import com.omgservers.service.operation.checkShard.CheckShardOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class GetPoolRuntimeServerContainerRefMethodImpl implements GetPoolRuntimeServerContainerRefMethod {

    final SelectPoolRuntimeServerContainerRefOperation selectPoolRuntimeServerContainerRefOperation;
    final CheckShardOperation checkShardOperation;

    final PgPool pgPool;

    @Override
    public Uni<GetPoolRuntimeServerContainerRefResponse> getPoolRuntimeServerContainerRef(
            final GetPoolRuntimeServerContainerRefRequest request) {
        log.debug("Get pool runtime server container ref, request={}", request);

        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shard -> {
                    final var poolId = request.getPoolId();
                    final var id = request.getId();
                    return pgPool.withTransaction(sqlConnection -> selectPoolRuntimeServerContainerRefOperation
                            .selectPoolRuntimeServerContainerRef(sqlConnection, shard.shard(), poolId, id));
                })
                .map(GetPoolRuntimeServerContainerRefResponse::new);
    }
}
