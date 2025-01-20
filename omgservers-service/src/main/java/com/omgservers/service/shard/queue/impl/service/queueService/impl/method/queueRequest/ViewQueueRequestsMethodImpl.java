package com.omgservers.service.shard.queue.impl.service.queueService.impl.method.queueRequest;

import com.omgservers.schema.module.queue.queueRequest.ViewQueueRequestsRequest;
import com.omgservers.schema.module.queue.queueRequest.ViewQueueRequestsResponse;
import com.omgservers.service.shard.queue.impl.operation.queueRequest.SelectActiveQueueRequestsByQueueIdOperation;
import com.omgservers.service.operation.server.CheckShardOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class ViewQueueRequestsMethodImpl implements ViewQueueRequestsMethod {

    final SelectActiveQueueRequestsByQueueIdOperation selectActiveQueueRequestsByQueueIdOperation;
    final CheckShardOperation checkShardOperation;

    final PgPool pgPool;

    @Override
    public Uni<ViewQueueRequestsResponse> execute(final ViewQueueRequestsRequest request) {
        log.trace("{}", request);

        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shard -> {
                    final var queueId = request.getQueueId();
                    return pgPool.withTransaction(
                            sqlConnection -> selectActiveQueueRequestsByQueueIdOperation
                                    .execute(sqlConnection,
                                            shard.shard(),
                                            queueId));
                })
                .map(ViewQueueRequestsResponse::new);
    }
}
