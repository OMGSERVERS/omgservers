package com.omgservers.service.shard.pool.impl.service.poolService.impl.method.pool;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.shard.pool.pool.GetPoolRequest;
import com.omgservers.schema.shard.pool.pool.GetPoolResponse;
import com.omgservers.service.shard.pool.impl.operation.pool.SelectPoolOperation;
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

    final PgPool pgPool;

    @Override
    public Uni<GetPoolResponse> execute(final ShardModel shardModel, final GetPoolRequest request) {
        log.debug("{}", request);

        final var id = request.getId();
        return pgPool.withTransaction(sqlConnection -> selectPoolOperation
                        .execute(sqlConnection, shardModel.slot(), id))
                .map(GetPoolResponse::new);
    }
}
