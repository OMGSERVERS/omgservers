package com.omgservers.service.module.pool.impl.service.poolService.impl.method.poolContainer;

import com.omgservers.schema.module.pool.poolContainer.GetPoolContainerRequest;
import com.omgservers.schema.module.pool.poolContainer.GetPoolContainerResponse;
import com.omgservers.service.module.pool.impl.operation.poolContainer.SelectPoolContainerOperation;
import com.omgservers.service.operation.server.CheckShardOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class GetPoolContainerMethodImpl implements GetPoolContainerMethod {

    final SelectPoolContainerOperation selectPoolContainerOperation;
    final CheckShardOperation checkShardOperation;

    final PgPool pgPool;

    @Override
    public Uni<GetPoolContainerResponse> execute(
            final GetPoolContainerRequest request) {
        log.trace("{}", request);

        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shard -> {
                    final var poolId = request.getPoolId();
                    final var serverId = request.getServerId();
                    final var id = request.getId();
                    return pgPool.withTransaction(sqlConnection -> selectPoolContainerOperation
                            .execute(sqlConnection, shard.shard(), poolId, serverId, id));
                })
                .map(GetPoolContainerResponse::new);
    }
}
