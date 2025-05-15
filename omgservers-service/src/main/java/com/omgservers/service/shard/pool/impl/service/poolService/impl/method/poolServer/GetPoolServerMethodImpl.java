package com.omgservers.service.shard.pool.impl.service.poolService.impl.method.poolServer;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.shard.pool.poolServer.GetPoolServerRequest;
import com.omgservers.schema.shard.pool.poolServer.GetPoolServerResponse;
import com.omgservers.service.shard.pool.impl.operation.poolServer.SelectPoolServerOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class GetPoolServerMethodImpl implements GetPoolServerMethod {

    final SelectPoolServerOperation selectPoolServerOperation;

    final PgPool pgPool;

    @Override
    public Uni<GetPoolServerResponse> execute(final ShardModel shardModel,
                                              final GetPoolServerRequest request) {
        log.debug("{}", request);

        final var poolId = request.getPoolId();
        final var id = request.getId();
        return pgPool.withTransaction(sqlConnection -> selectPoolServerOperation
                        .execute(sqlConnection, shardModel.slot(), poolId, id))
                .map(GetPoolServerResponse::new);
    }
}
