package com.omgservers.service.shard.pool.impl.service.poolService.impl.method.poolRequest;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.shard.pool.poolRequest.GetPoolRequestRequest;
import com.omgservers.schema.shard.pool.poolRequest.GetPoolRequestResponse;
import com.omgservers.service.shard.pool.impl.operation.poolRequest.SelectPoolRequestOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class GetPoolRequestMethodImpl implements GetPoolRequestMethod {

    final SelectPoolRequestOperation selectPoolRequestOperation;

    final PgPool pgPool;

    @Override
    public Uni<GetPoolRequestResponse> execute(final ShardModel shardModel,
                                               final GetPoolRequestRequest request) {
        log.debug("{}", request);

        final var poolId = request.getPoolId();
        final var id = request.getId();
        return pgPool.withTransaction(sqlConnection -> selectPoolRequestOperation
                        .execute(sqlConnection, shardModel.slot(), poolId, id))
                .map(GetPoolRequestResponse::new);
    }
}
