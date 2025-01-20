package com.omgservers.service.shard.queue.impl.service.queueService.impl.method.queueRequest;

import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
import com.omgservers.schema.module.queue.queueRequest.SyncQueueRequestRequest;
import com.omgservers.schema.module.queue.queueRequest.SyncQueueRequestResponse;
import com.omgservers.service.exception.ServerSideNotFoundException;
import com.omgservers.service.shard.queue.impl.operation.queue.VerifyQueueExistsOperation;
import com.omgservers.service.shard.queue.impl.operation.queueRequest.UpsertQueueRequestOperation;
import com.omgservers.service.operation.server.ChangeContext;
import com.omgservers.service.operation.server.ChangeWithContextOperation;
import com.omgservers.service.operation.server.CheckShardOperation;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class SyncQueueRequestMethodImpl implements SyncQueueRequestMethod {

    final UpsertQueueRequestOperation upsertQueueRequestOperation;
    final ChangeWithContextOperation changeWithContextOperation;
    final VerifyQueueExistsOperation verifyQueueExistsOperation;
    final CheckShardOperation checkShardOperation;

    @Override
    public Uni<SyncQueueRequestResponse> execute(final SyncQueueRequestRequest request) {
        log.trace("{}", request);

        final var shardKey = request.getRequestShardKey();
        final var queueRequest = request.getQueueRequest();
        final var queueId = queueRequest.getQueueId();

        return checkShardOperation.checkShard(shardKey)
                .flatMap(shardModel -> {
                    final var shard = shardModel.shard();
                    return changeWithContextOperation.<Boolean>changeWithContext(
                                    (context, sqlConnection) -> verifyQueueExistsOperation
                                            .execute(sqlConnection, shard, queueId)
                                            .flatMap(exists -> {
                                                if (exists) {
                                                    return upsertQueueRequestOperation.execute(
                                                            context,
                                                            sqlConnection,
                                                            shard,
                                                            queueRequest);
                                                } else {
                                                    throw new ServerSideNotFoundException(
                                                            ExceptionQualifierEnum.PARENT_NOT_FOUND,
                                                            "queue does not exist or was deleted, id=" + queueId);
                                                }
                                            }))
                            .map(ChangeContext::getResult);
                })
                .map(SyncQueueRequestResponse::new);
    }
}
