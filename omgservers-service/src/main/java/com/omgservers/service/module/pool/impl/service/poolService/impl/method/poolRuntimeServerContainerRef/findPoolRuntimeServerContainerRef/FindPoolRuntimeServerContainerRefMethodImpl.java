package com.omgservers.service.module.pool.impl.service.poolService.impl.method.poolRuntimeServerContainerRef.findPoolRuntimeServerContainerRef;

import com.omgservers.model.dto.pool.poolRuntimeServerContainerRef.FindPoolRuntimeServerContainerRefRequest;
import com.omgservers.model.dto.pool.poolRuntimeServerContainerRef.FindPoolRuntimeServerContainerRefResponse;
import com.omgservers.service.module.pool.impl.operation.poolRuntimeServerContainerRef.selectPoolRuntimeServerContainerRefByPoolIdAndContainerId.SelectPoolRuntimeServerContainerRefByPoolIdAndContainerIdOperation;
import com.omgservers.service.operation.checkShard.CheckShardOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class FindPoolRuntimeServerContainerRefMethodImpl implements FindPoolRuntimeServerContainerRefMethod {

    final SelectPoolRuntimeServerContainerRefByPoolIdAndContainerIdOperation
            selectPoolRuntimeServerContainerRefByPoolIdAndContainerIdOperation;
    final CheckShardOperation checkShardOperation;

    final PgPool pgPool;

    @Override
    public Uni<FindPoolRuntimeServerContainerRefResponse> findPoolRuntimeServerContainerRef(
            final FindPoolRuntimeServerContainerRefRequest request) {
        log.debug("Find pool runtime server container ref, request={}", request);

        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shard -> {
                    final var poolId = request.getPoolId();
                    final var serverId = request.getServerId();
                    final var containerId = request.getContainerId();
                    return pgPool.withTransaction(sqlConnection ->
                            selectPoolRuntimeServerContainerRefByPoolIdAndContainerIdOperation
                                    .selectPoolRuntimeServerContainerRefByPoolIdAndContainerId(sqlConnection,
                                            shard.shard(),
                                            poolId,
                                            serverId,
                                            containerId));
                })
                .map(FindPoolRuntimeServerContainerRefResponse::new);
    }
}
