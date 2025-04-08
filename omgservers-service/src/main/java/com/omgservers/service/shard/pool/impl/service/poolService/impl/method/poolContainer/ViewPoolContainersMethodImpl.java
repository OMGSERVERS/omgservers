package com.omgservers.service.shard.pool.impl.service.poolService.impl.method.poolContainer;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.module.pool.poolContainer.ViewPoolContainersRequest;
import com.omgservers.schema.module.pool.poolContainer.ViewPoolContainersResponse;
import com.omgservers.service.shard.pool.impl.operation.poolContainer.SelectActivePoolContainersByPoolIdAndServerIdOperation;
import com.omgservers.service.shard.pool.impl.operation.poolContainer.SelectActivePoolContainersByPoolIdOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class ViewPoolContainersMethodImpl implements ViewPoolContainersMethod {

    final SelectActivePoolContainersByPoolIdOperation
            selectActivePoolContainersByPoolIdOperation;
    final SelectActivePoolContainersByPoolIdAndServerIdOperation
            selectActivePoolContainersByPoolIdAndServerIdOperation;

    final PgPool pgPool;

    @Override
    public Uni<ViewPoolContainersResponse> execute(final ShardModel shardModel,
                                                   final ViewPoolContainersRequest request) {
        log.trace("{}", request);

        final var poolId = request.getPoolId();
        return pgPool.withTransaction(sqlConnection -> {
                    final var serverId = request.getServerId();
                    if (Objects.nonNull(serverId)) {
                        return selectActivePoolContainersByPoolIdAndServerIdOperation
                                .execute(sqlConnection,
                                        shardModel.shard(),
                                        poolId,
                                        serverId);
                    } else {
                        return selectActivePoolContainersByPoolIdOperation
                                .execute(sqlConnection,
                                        shardModel.shard(),
                                        poolId);
                    }
                })
                .map(ViewPoolContainersResponse::new);
    }
}
