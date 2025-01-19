package com.omgservers.service.module.queue.impl.service.queueService.impl.method.queue;

import com.omgservers.schema.module.queue.queue.GetQueueRequest;
import com.omgservers.schema.module.queue.queue.GetQueueResponse;
import com.omgservers.service.module.queue.impl.operation.queue.SelectQueueOperation;
import com.omgservers.service.operation.server.CheckShardOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class GetQueueMethodImpl implements GetQueueMethod {

    final SelectQueueOperation selectQueueOperation;
    final CheckShardOperation checkShardOperation;

    final PgPool pgPool;

    @Override
    public Uni<GetQueueResponse> execute(final GetQueueRequest request) {
        log.trace("{}", request);

        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shard -> {
                    final var id = request.getId();
                    return pgPool.withTransaction(sqlConnection -> selectQueueOperation
                            .execute(sqlConnection, shard.shard(), id));
                })
                .map(GetQueueResponse::new);
    }
}
