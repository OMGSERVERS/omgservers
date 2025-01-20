package com.omgservers.service.shard.queue.impl.service.queueService.impl.method.queueRequest;

import com.omgservers.schema.module.queue.queueRequest.DeleteQueueRequestRequest;
import com.omgservers.schema.module.queue.queueRequest.DeleteQueueRequestResponse;
import com.omgservers.service.shard.queue.impl.operation.queueRequest.DeleteQueueRequestOperation;
import com.omgservers.service.operation.server.ChangeContext;
import com.omgservers.service.operation.server.ChangeWithContextOperation;
import com.omgservers.service.operation.server.CheckShardOperation;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = lombok.AccessLevel.PACKAGE)
class DeleteQueueRequestMethodImpl implements DeleteQueueRequestMethod {

    final DeleteQueueRequestOperation deleteQueueRequestOperation;
    final ChangeWithContextOperation changeWithContextOperation;
    final CheckShardOperation checkShardOperation;

    @Override
    public Uni<DeleteQueueRequestResponse> execute(
            final DeleteQueueRequestRequest request) {
        log.trace("{}", request);

        final var queueId = request.getQueueId();
        final var id = request.getId();
        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shardModel -> changeWithContextOperation.<Boolean>changeWithContext(
                                (changeContext, sqlConnection) -> deleteQueueRequestOperation
                                        .execute(changeContext,
                                                sqlConnection,
                                                shardModel.shard(),
                                                queueId,
                                                id))
                        .map(ChangeContext::getResult))
                .map(DeleteQueueRequestResponse::new);
    }
}
