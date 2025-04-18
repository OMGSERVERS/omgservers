package com.omgservers.service.shard.pool.impl.service.poolService.impl.method.poolContainer;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.shard.pool.poolContainer.FindPoolContainerRequest;
import com.omgservers.schema.shard.pool.poolContainer.FindPoolContainerResponse;
import com.omgservers.service.shard.pool.impl.operation.poolContainer.SelectPoolContainerByPoolIdAndRuntimeIdOperation;
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

    final PgPool pgPool;

    @Override
    public Uni<FindPoolContainerResponse> execute(final ShardModel shardModel,
                                                  final FindPoolContainerRequest request) {
        log.trace("{}", request);

        final var poolId = request.getPoolId();
        final var runtimeId = request.getRuntimeId();
        return pgPool.withTransaction(sqlConnection ->
                        selectPoolContainerByPoolIdAndRuntimeIdOperation
                                .execute(sqlConnection,
                                        shardModel.slot(),
                                        poolId,
                                        runtimeId))
                .map(FindPoolContainerResponse::new);
    }
}
