package com.omgservers.service.operation.queue;

import com.omgservers.schema.model.queueRequest.QueueRequestModel;
import com.omgservers.schema.module.queue.queueRequest.DeleteQueueRequestRequest;
import com.omgservers.schema.module.queue.queueRequest.DeleteQueueRequestResponse;
import com.omgservers.schema.module.queue.queueRequest.ViewQueueRequestsRequest;
import com.omgservers.schema.module.queue.queueRequest.ViewQueueRequestsResponse;
import com.omgservers.service.exception.ServerSideClientException;
import com.omgservers.service.shard.queue.QueueShard;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class DeleteQueueRequestsOperationImpl implements DeleteQueueRequestsOperation {

    final QueueShard queueShard;

    @Override
    public Uni<Void> execute(final Long queueId) {
        return viewQueueRequests(queueId)
                .flatMap(queueRequests -> Multi.createFrom().iterable(queueRequests)
                        .onItem().transformToUniAndConcatenate(queueRequest ->
                                deleteQueueRequest(queueId, queueRequest.getId())
                                        .onFailure(ServerSideClientException.class)
                                        .recoverWithItem(t -> {
                                            log.warn("Failed to delete queue request, " +
                                                            "queueRequest={}/{}" +
                                                            "{}:{}",
                                                    queueId,
                                                    queueRequest.getId(),
                                                    t.getClass().getSimpleName(),
                                                    t.getMessage());
                                            return null;
                                        })
                        )
                        .collect().asList()
                        .replaceWithVoid()
                );
    }

    Uni<List<QueueRequestModel>> viewQueueRequests(final Long queueId) {
        final var request = new ViewQueueRequestsRequest(queueId);
        return queueShard.getQueueService().execute(request)
                .map(ViewQueueRequestsResponse::getQueueRequests);
    }

    Uni<Boolean> deleteQueueRequest(final Long queueId, final Long id) {
        final var request = new DeleteQueueRequestRequest(queueId, id);
        return queueShard.getQueueService().execute(request)
                .map(DeleteQueueRequestResponse::getDeleted);
    }
}
