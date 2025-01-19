package com.omgservers.service.module.queue.impl.service.queueService.impl.method.queue;

import com.omgservers.schema.module.queue.queue.DeleteQueueRequest;
import com.omgservers.schema.module.queue.queue.DeleteQueueResponse;
import com.omgservers.service.module.queue.impl.operation.queue.DeleteQueueOperation;
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
class DeleteQueueMethodImpl implements DeleteQueueMethod {

    final ChangeWithContextOperation changeWithContextOperation;
    final DeleteQueueOperation deleteQueueOperation;
    final CheckShardOperation checkShardOperation;

    @Override
    public Uni<DeleteQueueResponse> execute(final DeleteQueueRequest request) {
        log.trace("{}", request);

        final var id = request.getId();
        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shardModel -> changeWithContextOperation.<Boolean>changeWithContext(
                                (changeContext, sqlConnection) -> deleteQueueOperation.execute(changeContext,
                                        sqlConnection,
                                        shardModel.shard(),
                                        id))
                        .map(ChangeContext::getResult))
                .map(DeleteQueueResponse::new);
    }
}
