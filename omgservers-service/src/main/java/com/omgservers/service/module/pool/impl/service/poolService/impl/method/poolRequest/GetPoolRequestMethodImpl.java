package com.omgservers.service.module.pool.impl.service.poolService.impl.method.poolRequest;

import com.omgservers.schema.module.pool.poolRequest.GetPoolRequestRequest;
import com.omgservers.schema.module.pool.poolRequest.GetPoolRequestResponse;
import com.omgservers.service.module.pool.impl.operation.poolRequest.SelectPoolRequestOperation;
import com.omgservers.service.operation.checkShard.CheckShardOperation;
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
    final CheckShardOperation checkShardOperation;

    final PgPool pgPool;

    @Override
    public Uni<GetPoolRequestResponse> execute(
            final GetPoolRequestRequest request) {
        log.debug("Requested, {}", request);

        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shard -> {
                    final var poolId = request.getPoolId();
                    final var id = request.getId();
                    return pgPool.withTransaction(sqlConnection -> selectPoolRequestOperation
                            .execute(sqlConnection, shard.shard(), poolId, id));
                })
                .map(GetPoolRequestResponse::new);
    }
}
