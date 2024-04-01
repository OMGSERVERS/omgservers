package com.omgservers.service.module.pool.impl.service.poolService.impl.method.poolServerRef.viewPoolServerRefs;

import com.omgservers.model.dto.pool.poolServerRef.ViewPoolServerRefsRequest;
import com.omgservers.model.dto.pool.poolServerRef.ViewPoolServerRefsResponse;
import com.omgservers.service.module.pool.impl.operation.poolServerRef.selectActivePoolServerRefsByPoolId.SelectActivePoolServerRefsByPoolIdOperation;
import com.omgservers.service.operation.checkShard.CheckShardOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class ViewPoolServerRefsMethodImpl implements ViewPoolServerRefsMethod {

    final SelectActivePoolServerRefsByPoolIdOperation selectActivePoolServerRefsByPoolIdOperation;
    final CheckShardOperation checkShardOperation;

    final PgPool pgPool;

    @Override
    public Uni<ViewPoolServerRefsResponse> viewPoolServerRefs(final ViewPoolServerRefsRequest request) {
        log.debug("View pool server refs, request={}", request);

        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shard -> {
                    final var poolId = request.getPoolId();
                    return pgPool.withTransaction(sqlConnection -> selectActivePoolServerRefsByPoolIdOperation
                            .selectActivePoolServerRefsByPoolId(sqlConnection,
                                    shard.shard(),
                                    poolId));
                })
                .map(ViewPoolServerRefsResponse::new);
    }
}
