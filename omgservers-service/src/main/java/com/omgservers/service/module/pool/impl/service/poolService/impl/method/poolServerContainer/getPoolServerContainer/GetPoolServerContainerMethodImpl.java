package com.omgservers.service.module.pool.impl.service.poolService.impl.method.poolServerContainer.getPoolServerContainer;

import com.omgservers.schema.module.pool.poolServerContainer.GetPoolServerContainerRequest;
import com.omgservers.schema.module.pool.poolServerContainer.GetPoolServerContainerResponse;
import com.omgservers.service.module.pool.impl.operation.poolServerContainer.selectPoolServerContainer.SelectPoolServerContainerOperation;
import com.omgservers.service.server.operation.checkShard.CheckShardOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class GetPoolServerContainerMethodImpl implements GetPoolServerContainerMethod {

    final SelectPoolServerContainerOperation selectPoolServerContainerOperation;
    final CheckShardOperation checkShardOperation;

    final PgPool pgPool;

    @Override
    public Uni<GetPoolServerContainerResponse> getPoolServerContainer(
            final GetPoolServerContainerRequest request) {
        log.debug("Get pool server container, request={}", request);

        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shard -> {
                    final var poolId = request.getPoolId();
                    final var serverId = request.getServerId();
                    final var id = request.getId();
                    return pgPool.withTransaction(sqlConnection -> selectPoolServerContainerOperation
                            .selectPoolServerContainer(sqlConnection, shard.shard(), poolId, serverId, id));
                })
                .map(GetPoolServerContainerResponse::new);
    }
}
