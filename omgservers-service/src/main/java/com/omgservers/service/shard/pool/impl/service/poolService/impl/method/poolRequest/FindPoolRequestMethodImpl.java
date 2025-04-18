package com.omgservers.service.shard.pool.impl.service.poolService.impl.method.poolRequest;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.shard.pool.poolRequest.FindPoolRequestRequest;
import com.omgservers.schema.shard.pool.poolRequest.FindPoolRequestResponse;
import com.omgservers.service.shard.pool.impl.operation.poolRequest.SelectPoolRequestByPoolIdAndRuntimeIdOperation;
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

    final PgPool pgPool;

    @Override
    public Uni<FindPoolRequestResponse> execute(final ShardModel shardModel,
                                                final FindPoolRequestRequest request) {
        log.trace("{}", request);

        final var lobbyId = request.getPoolId();
        final var runtimeId = request.getRuntimeId();
        return pgPool.withTransaction(sqlConnection -> selectPoolRequestByPoolIdAndRuntimeIdOperation
                        .execute(sqlConnection,
                                shardModel.slot(),
                                lobbyId,
                                runtimeId))
                .map(FindPoolRequestResponse::new);
    }
}
