package com.omgservers.service.module.pool.impl.service.poolService.impl.method.poolRuntimeServerContainerRequest.findPoolRuntimeServerContainerRequest;

import com.omgservers.model.dto.pool.poolRuntimeServerContainerRequest.FindPoolRuntimeServerContainerRequestRequest;
import com.omgservers.model.dto.pool.poolRuntimeServerContainerRequest.FindPoolRuntimeServerContainerRequestResponse;
import com.omgservers.service.module.pool.impl.operation.poolRuntimeServerContainerRequest.selectPoolRuntimeServerContainerRequestByPoolIdAndRuntimeId.SelectPoolRuntimeServerContainerRequestByPoolIdAndRuntimeIdOperation;
import com.omgservers.service.operation.checkShard.CheckShardOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class FindPoolRuntimeServerContainerRequestMethodImpl implements FindPoolRuntimeServerContainerRequestMethod {

    final SelectPoolRuntimeServerContainerRequestByPoolIdAndRuntimeIdOperation
            selectPoolRuntimeServerContainerRequestByPoolIdAndRuntimeIdOperation;
    final CheckShardOperation checkShardOperation;

    final PgPool pgPool;

    @Override
    public Uni<FindPoolRuntimeServerContainerRequestResponse> findPoolRuntimeServerContainerRequest(
            final FindPoolRuntimeServerContainerRequestRequest request) {
        log.debug("Find pool runtime server container request, request={}", request);

        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shard -> {
                    final var lobbyId = request.getPoolId();
                    final var runtimeId = request.getRuntimeId();
                    return pgPool.withTransaction(sqlConnection ->
                            selectPoolRuntimeServerContainerRequestByPoolIdAndRuntimeIdOperation
                                    .selectPoolRuntimeServerContainerRequestByPoolIdAndRuntimeId(sqlConnection,
                                            shard.shard(),
                                            lobbyId,
                                            runtimeId));
                })
                .map(FindPoolRuntimeServerContainerRequestResponse::new);
    }
}
