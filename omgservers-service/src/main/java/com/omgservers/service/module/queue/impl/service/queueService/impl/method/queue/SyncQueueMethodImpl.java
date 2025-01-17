package com.omgservers.service.module.queue.impl.service.queueService.impl.method.queue;

import com.omgservers.schema.module.queue.queue.SyncQueueRequest;
import com.omgservers.schema.module.queue.queue.SyncQueueResponse;
import com.omgservers.service.module.queue.impl.operation.queue.UpsertQueueOperation;
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
class SyncQueueMethodImpl implements SyncQueueMethod {

    final ChangeWithContextOperation changeWithContextOperation;
    final UpsertQueueOperation upsertQueueOperation;
    final CheckShardOperation checkShardOperation;

    @Override
    public Uni<SyncQueueResponse> execute(final SyncQueueRequest request) {
        log.trace("{}", request);

        final var queue = request.getQueue();
        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shardModel -> {
                    final var shard = shardModel.shard();
                    return changeWithContextOperation.<Boolean>changeWithContext(
                                    (changeContext, sqlConnection) -> upsertQueueOperation.execute(
                                            changeContext,
                                            sqlConnection,
                                            shard,
                                            queue))
                            .map(ChangeContext::getResult);
                })
                .map(SyncQueueResponse::new);
    }
}
