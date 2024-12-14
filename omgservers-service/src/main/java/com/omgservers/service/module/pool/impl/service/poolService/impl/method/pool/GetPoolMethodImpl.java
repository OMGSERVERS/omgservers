package com.omgservers.service.module.pool.impl.service.poolService.impl.method.pool;

import com.omgservers.schema.module.pool.pool.GetPoolRequest;
import com.omgservers.schema.module.pool.pool.GetPoolResponse;
import com.omgservers.service.module.pool.impl.operation.pool.SelectPoolOperation;
import com.omgservers.service.operation.checkShard.CheckShardOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class GetPoolMethodImpl implements GetPoolMethod {

    final SelectPoolOperation selectPoolOperation;
    final CheckShardOperation checkShardOperation;

    final PgPool pgPool;

    @Override
    public Uni<GetPoolResponse> execute(final GetPoolRequest request) {
        log.trace("Requested, {}", request);

        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shard -> {
                    final var id = request.getId();
                    return pgPool.withTransaction(sqlConnection -> selectPoolOperation
                            .execute(sqlConnection, shard.shard(), id));
                })
                .map(GetPoolResponse::new);
    }
}
