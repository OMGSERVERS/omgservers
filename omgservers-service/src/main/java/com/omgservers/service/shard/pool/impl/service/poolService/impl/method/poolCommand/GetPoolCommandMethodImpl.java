package com.omgservers.service.shard.pool.impl.service.poolService.impl.method.poolCommand;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.module.pool.poolCommand.GetPoolCommandRequest;
import com.omgservers.schema.module.pool.poolCommand.GetPoolCommandResponse;
import com.omgservers.service.shard.pool.impl.operation.poolCommand.SelectPoolCommandOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class GetPoolCommandMethodImpl implements GetPoolCommandMethod {

    final SelectPoolCommandOperation selectPoolCommandOperation;

    final PgPool pgPool;

    @Override
    public Uni<GetPoolCommandResponse> execute(final ShardModel shardModel,
                                               final GetPoolCommandRequest request) {
        log.trace("{}", request);

        final var poolId = request.getPoolId();
        final var id = request.getId();
        return pgPool.withTransaction(sqlConnection -> selectPoolCommandOperation
                        .execute(sqlConnection, shardModel.shard(), poolId, id))
                .map(GetPoolCommandResponse::new);
    }
}
