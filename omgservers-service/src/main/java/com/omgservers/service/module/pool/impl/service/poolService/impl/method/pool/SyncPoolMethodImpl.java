package com.omgservers.service.module.pool.impl.service.poolService.impl.method.pool;

import com.omgservers.schema.module.pool.pool.SyncPoolRequest;
import com.omgservers.schema.module.pool.pool.SyncPoolResponse;
import com.omgservers.service.module.pool.impl.operation.pool.UpsertPoolOperation;
import com.omgservers.service.operation.changeWithContext.ChangeContext;
import com.omgservers.service.operation.changeWithContext.ChangeWithContextOperation;
import com.omgservers.service.operation.checkShard.CheckShardOperation;
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
        log.trace("Requested, {}", request);

        final var pool = request.getPool();
        return Uni.createFrom().voidItem()
                .flatMap(voidItem -> checkShardOperation.checkShard(request.getRequestShardKey()))
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
