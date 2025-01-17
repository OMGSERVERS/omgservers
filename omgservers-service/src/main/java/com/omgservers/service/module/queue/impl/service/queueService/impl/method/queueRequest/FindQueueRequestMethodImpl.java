package com.omgservers.service.module.queue.impl.service.queueService.impl.method.queueRequest;

import com.omgservers.schema.module.queue.queueRequest.FindQueueRequestRequest;
import com.omgservers.schema.module.queue.queueRequest.FindQueueRequestResponse;
import com.omgservers.service.module.queue.impl.operation.queueRequest.SelectQueueRequestByQueueIdAndClientIdOperation;
import com.omgservers.service.operation.checkShard.CheckShardOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class FindQueueRequestMethodImpl implements FindQueueRequestMethod {

    final SelectQueueRequestByQueueIdAndClientIdOperation
            selectQueueRequestByQueueIdAndClientIdOperation;
    final CheckShardOperation checkShardOperation;

    final PgPool pgPool;

    @Override
    public Uni<FindQueueRequestResponse> execute(final FindQueueRequestRequest request) {
        log.trace("{}", request);

        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shard -> {
                    final var lobbyId = request.getQueueId();
                    final var clientId = request.getClientId();
                    return pgPool.withTransaction(sqlConnection ->
                            selectQueueRequestByQueueIdAndClientIdOperation
                                    .execute(sqlConnection,
                                            shard.shard(),
                                            lobbyId,
                                            clientId));
                })
                .map(FindQueueRequestResponse::new);
    }
}
