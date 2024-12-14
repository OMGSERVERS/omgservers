package com.omgservers.service.module.pool.impl.service.poolService.impl.method.poolRequest;

import com.omgservers.schema.module.pool.poolRequest.FindPoolRequestRequest;
import com.omgservers.schema.module.pool.poolRequest.FindPoolRequestResponse;
import com.omgservers.service.module.pool.impl.operation.poolRequest.SelectPoolRequestByPoolIdAndRuntimeIdOperation;
import com.omgservers.service.operation.checkShard.CheckShardOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class FindPoolRequestMethodImpl implements FindPoolRequestMethod {

    final SelectPoolRequestByPoolIdAndRuntimeIdOperation
            selectPoolRequestByPoolIdAndRuntimeIdOperation;
    final CheckShardOperation checkShardOperation;

    final PgPool pgPool;

    @Override
    public Uni<FindPoolRequestResponse> execute(
            final FindPoolRequestRequest request) {
        log.trace("Requested, {}", request);

        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shard -> {
                    final var lobbyId = request.getPoolId();
                    final var runtimeId = request.getRuntimeId();
                    return pgPool.withTransaction(sqlConnection ->
                            selectPoolRequestByPoolIdAndRuntimeIdOperation
                                    .execute(sqlConnection,
                                            shard.shard(),
                                            lobbyId,
                                            runtimeId));
                })
                .map(FindPoolRequestResponse::new);
    }
}
