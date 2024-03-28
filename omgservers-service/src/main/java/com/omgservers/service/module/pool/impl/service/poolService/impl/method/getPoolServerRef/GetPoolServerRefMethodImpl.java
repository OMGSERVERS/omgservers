package com.omgservers.service.module.pool.impl.service.poolService.impl.method.getPoolServerRef;

import com.omgservers.model.dto.pool.GetPoolServerRefRequest;
import com.omgservers.model.dto.pool.GetPoolServerRefResponse;
import com.omgservers.service.module.pool.impl.operation.selectPoolServerRef.SelectPoolServerRefOperation;
import com.omgservers.service.operation.checkShard.CheckShardOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class GetPoolServerRefMethodImpl implements GetPoolServerRefMethod {

    final SelectPoolServerRefOperation selectPoolServerRefOperation;
    final CheckShardOperation checkShardOperation;

    final PgPool pgPool;

    @Override
    public Uni<GetPoolServerRefResponse> getPoolServerRef(final GetPoolServerRefRequest request) {
        log.debug("Get pool server ref, request={}", request);

        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shard -> {
                    final var lobbyId = request.getPoolId();
                    final var id = request.getId();
                    return pgPool.withTransaction(sqlConnection -> selectPoolServerRefOperation
                            .selectPoolServerRef(sqlConnection, shard.shard(), lobbyId, id));
                })
                .map(GetPoolServerRefResponse::new);
    }
}
