package com.omgservers.service.module.pool.impl.service.poolService.impl.method.poolServerContainer.findPoolServerContainer;

import com.omgservers.schema.module.pool.poolServerContainer.FindPoolServerContainerRequest;
import com.omgservers.schema.module.pool.poolServerContainer.FindPoolServerContainerResponse;
import com.omgservers.service.module.pool.impl.operation.poolServerContainer.selectPoolServerContainerByPoolIdAndRuntimeId.SelectPoolServerContainerByPoolIdAndRuntimeIdOperation;
import com.omgservers.service.server.operation.checkShard.CheckShardOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class FindPoolServerContainerMethodImpl implements FindPoolServerContainerMethod {

    final SelectPoolServerContainerByPoolIdAndRuntimeIdOperation
            selectPoolServerContainerByPoolIdAndRuntimeIdOperation;
    final CheckShardOperation checkShardOperation;

    final PgPool pgPool;

    @Override
    public Uni<FindPoolServerContainerResponse> findPoolServerContainer(
            final FindPoolServerContainerRequest request) {
        log.debug("Find pool server container, request={}", request);

        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shard -> {
                    final var poolId = request.getPoolId();
                    final var serverId = request.getServerId();
                    final var runtimeId = request.getRuntimeId();
                    return pgPool.withTransaction(sqlConnection ->
                            selectPoolServerContainerByPoolIdAndRuntimeIdOperation
                                    .selectPoolServerContainerByPoolIdAndRuntimeId(sqlConnection,
                                            shard.shard(),
                                            poolId,
                                            serverId,
                                            runtimeId));
                })
                .map(FindPoolServerContainerResponse::new);
    }
}
