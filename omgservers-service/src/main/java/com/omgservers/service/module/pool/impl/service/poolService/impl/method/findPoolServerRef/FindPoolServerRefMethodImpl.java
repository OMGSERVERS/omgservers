package com.omgservers.service.module.pool.impl.service.poolService.impl.method.findPoolServerRef;

import com.omgservers.model.dto.pool.FindPoolServerRefRequest;
import com.omgservers.model.dto.pool.FindPoolServerRefResponse;
import com.omgservers.service.module.pool.impl.operation.selectPoolServerRefByPoolIdAndServerId.SelectPoolServerByPoolIdAndServerIdOperation;
import com.omgservers.service.operation.checkShard.CheckShardOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class FindPoolServerRefMethodImpl implements FindPoolServerRefMethod {

    final SelectPoolServerByPoolIdAndServerIdOperation selectPoolServerByPoolIdAndServerIdOperation;
    final CheckShardOperation checkShardOperation;

    final PgPool pgPool;

    @Override
    public Uni<FindPoolServerRefResponse> findPoolServerRef(final FindPoolServerRefRequest request) {
        log.debug("Find pool server ref, request={}", request);

        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shard -> {
                    final var lobbyId = request.getPoolId();
                    final var serverId = request.getServerId();
                    return pgPool.withTransaction(sqlConnection -> selectPoolServerByPoolIdAndServerIdOperation
                            .selectPoolServerRefByPoolIdAndServerId(sqlConnection,
                                    shard.shard(),
                                    lobbyId,
                                    serverId));
                })
                .map(FindPoolServerRefResponse::new);
    }
}
