package com.omgservers.service.module.pool.impl.service.poolService.impl.method.poolServerContainer.viewPoolServerContainers;

import com.omgservers.schema.module.pool.poolServerContainer.ViewPoolServerContainersRequest;
import com.omgservers.schema.module.pool.poolServerContainer.ViewPoolServerContainersResponse;
import com.omgservers.service.module.pool.impl.operation.poolServerContainer.selectActivePoolServerContainersByPoolId.SelectActivePoolServerContainersByPoolIdOperation;
import com.omgservers.service.module.pool.impl.operation.poolServerContainer.selectActivePoolServerContainersByPoolIdAndServerId.SelectActivePoolServerContainersByPoolIdAndServerIdOperation;
import com.omgservers.service.operation.checkShard.CheckShardOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class ViewPoolServerContainersMethodImpl implements ViewPoolServerContainersMethod {

    final SelectActivePoolServerContainersByPoolIdOperation
            selectActivePoolServerContainersByPoolIdOperation;
    final SelectActivePoolServerContainersByPoolIdAndServerIdOperation
            selectActivePoolServerContainersByPoolIdAndServerIdOperation;
    final CheckShardOperation checkShardOperation;

    final PgPool pgPool;

    @Override
    public Uni<ViewPoolServerContainersResponse> viewPoolServerContainers(
            final ViewPoolServerContainersRequest request) {
        log.debug("View pool server containers, request={}", request);

        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shard -> {
                    final var poolId = request.getPoolId();

                    return pgPool.withTransaction(
                            sqlConnection -> {
                                final var serverId = request.getServerId();
                                if (Objects.nonNull(serverId)) {
                                    return selectActivePoolServerContainersByPoolIdAndServerIdOperation
                                            .selectActivePoolServerContainersByPoolIdAndServerId(sqlConnection,
                                                    shard.shard(),
                                                    poolId,
                                                    serverId);
                                } else {
                                    return selectActivePoolServerContainersByPoolIdOperation
                                            .selectActivePoolServerContainersByPoolId(sqlConnection,
                                                    shard.shard(),
                                                    poolId);
                                }
                            });
                })
                .map(ViewPoolServerContainersResponse::new);
    }
}
