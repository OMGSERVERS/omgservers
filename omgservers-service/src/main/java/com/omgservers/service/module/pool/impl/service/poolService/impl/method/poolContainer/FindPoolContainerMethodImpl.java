package com.omgservers.service.module.pool.impl.service.poolService.impl.method.poolContainer;

import com.omgservers.schema.module.pool.poolContainer.FindPoolContainerRequest;
import com.omgservers.schema.module.pool.poolContainer.FindPoolContainerResponse;
import com.omgservers.service.module.pool.impl.operation.poolContainer.SelectPoolContainerByPoolIdAndRuntimeIdOperation;
import com.omgservers.service.operation.server.CheckShardOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class FindPoolContainerMethodImpl implements FindPoolContainerMethod {

    final SelectPoolContainerByPoolIdAndRuntimeIdOperation
            selectPoolContainerByPoolIdAndRuntimeIdOperation;
    final CheckShardOperation checkShardOperation;

    final PgPool pgPool;

    @Override
    public Uni<FindPoolContainerResponse> execute(
            final FindPoolContainerRequest request) {
        log.trace("{}", request);

        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shard -> {
                    final var poolId = request.getPoolId();
                    final var serverId = request.getServerId();
                    final var runtimeId = request.getRuntimeId();
                    return pgPool.withTransaction(sqlConnection ->
                            selectPoolContainerByPoolIdAndRuntimeIdOperation
                                    .execute(sqlConnection,
                                            shard.shard(),
                                            poolId,
                                            serverId,
                                            runtimeId));
                })
                .map(FindPoolContainerResponse::new);
    }
}
