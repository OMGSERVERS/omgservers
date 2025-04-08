package com.omgservers.service.shard.pool.impl.service.poolService.impl.method.pool;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.module.pool.pool.SyncPoolRequest;
import com.omgservers.schema.module.pool.pool.SyncPoolResponse;
import com.omgservers.service.operation.server.ChangeContext;
import com.omgservers.service.operation.server.ChangeWithContextOperation;
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

    @Override
    public Uni<SyncPoolResponse> execute(final ShardModel shardModel,
                                         final SyncPoolRequest request) {
        log.trace("{}", request);

        final var pool = request.getPool();
        return changeWithContextOperation.<Boolean>changeWithContext(
                        (changeContext, sqlConnection) -> upsertPoolOperation.execute(
                                changeContext,
                                sqlConnection,
                                shardModel.shard(),
                                pool))
                .map(ChangeContext::getResult)
                .map(SyncPoolResponse::new);
    }
}
