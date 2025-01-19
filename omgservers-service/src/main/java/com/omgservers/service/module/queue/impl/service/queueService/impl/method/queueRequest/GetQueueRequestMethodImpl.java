package com.omgservers.service.module.queue.impl.service.queueService.impl.method.queueRequest;

import com.omgservers.schema.module.queue.queueRequest.GetQueueRequestRequest;
import com.omgservers.schema.module.queue.queueRequest.GetQueueRequestResponse;
import com.omgservers.service.module.queue.impl.operation.queueRequest.SelectQueueRequestOperation;
import com.omgservers.service.operation.server.CheckShardOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class GetQueueRequestMethodImpl implements GetQueueRequestMethod {

    final SelectQueueRequestOperation selectQueueRequestOperation;
    final CheckShardOperation checkShardOperation;

    final PgPool pgPool;

    @Override
    public Uni<GetQueueRequestResponse> execute(
            final GetQueueRequestRequest request) {
        log.trace("{}", request);

        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shard -> {
                    final var queueId = request.getQueueId();
                    final var id = request.getId();
                    return pgPool.withTransaction(sqlConnection -> selectQueueRequestOperation
                            .execute(sqlConnection, shard.shard(), queueId, id));
                })
                .map(GetQueueRequestResponse::new);
    }
}
