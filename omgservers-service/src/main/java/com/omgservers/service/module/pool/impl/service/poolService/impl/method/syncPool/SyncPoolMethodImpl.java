package com.omgservers.service.module.pool.impl.service.poolService.impl.method.syncPool;

import com.omgservers.model.dto.pool.SyncPoolRequest;
import com.omgservers.model.dto.pool.SyncPoolResponse;
import com.omgservers.service.module.pool.impl.operation.upsertPool.UpsertPoolOperation;
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
    public Uni<SyncPoolResponse> syncPool(final SyncPoolRequest request) {
        log.debug("Sync pool, request={}", request);

        final var pool = request.getPool();
        return Uni.createFrom().voidItem()
                .flatMap(voidItem -> checkShardOperation.checkShard(request.getRequestShardKey()))
                .flatMap(shardModel -> {
                    final var shard = shardModel.shard();
                    return changeWithContextOperation.<Boolean>changeWithContext(
                                    (changeContext, sqlConnection) -> upsertPoolOperation.upsertPool(
                                            changeContext,
                                            sqlConnection,
                                            shard,
                                            pool))
                            .map(ChangeContext::getResult);
                })
                .map(SyncPoolResponse::new);
    }
}
