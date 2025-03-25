package com.omgservers.service.shard.pool.impl.service.poolService.impl.method.pool;

import com.omgservers.schema.module.pool.pool.SyncPoolRequest;
import com.omgservers.schema.module.pool.pool.SyncPoolResponse;
import com.omgservers.service.operation.server.ChangeContext;
import com.omgservers.service.operation.server.ChangeWithContextOperation;
import com.omgservers.service.operation.server.CheckShardOperation;
import com.omgservers.service.shard.pool.impl.operation.pool.UpsertPoolOperation;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class SyncPoolMethodImpl implements SyncPoolMethod {

    final ChangeWithContextOperation changeWithContextOperation;
    final UpsertPoolOperation upsertPoolOperation;
    final CheckShardOperation checkShardOperation;

    @Override
    public Uni<SyncPoolResponse> execute(final SyncPoolRequest request) {
        log.trace("{}", request);

        final var pool = request.getPool();
        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shardModel -> {
                    final var shard = shardModel.shard();
                    return changeWithContextOperation.<Boolean>changeWithContext(
                                    (changeContext, sqlConnection) -> upsertPoolOperation.execute(
                                            changeContext,
                                            sqlConnection,
                                            shard,
                                            pool))
                            .map(ChangeContext::getResult);
                })
                .map(SyncPoolResponse::new);
    }
}
