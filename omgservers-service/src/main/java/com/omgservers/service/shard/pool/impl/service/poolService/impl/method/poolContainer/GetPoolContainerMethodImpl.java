package com.omgservers.service.shard.pool.impl.service.poolService.impl.method.poolContainer;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.shard.pool.poolContainer.GetPoolContainerRequest;
import com.omgservers.schema.shard.pool.poolContainer.GetPoolContainerResponse;
import com.omgservers.service.shard.pool.impl.operation.poolContainer.SelectPoolContainerOperation;
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

    final PgPool pgPool;

    @Override
    public Uni<GetPoolContainerResponse> execute(final ShardModel shardModel,
                                                 final GetPoolContainerRequest request) {
        log.trace("{}", request);

        final var poolId = request.getPoolId();
        final var id = request.getId();
        return pgPool.withTransaction(sqlConnection -> selectPoolContainerOperation
                        .execute(sqlConnection, shardModel.slot(), poolId, id))
                .map(GetPoolContainerResponse::new);
    }
}
