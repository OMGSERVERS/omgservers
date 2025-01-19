package com.omgservers.service.operation.queue;

import com.omgservers.schema.model.queue.QueueModel;
import com.omgservers.schema.model.queueRequest.QueueRequestModel;
import com.omgservers.schema.module.queue.queue.GetQueueRequest;
import com.omgservers.schema.module.queue.queue.GetQueueResponse;
import com.omgservers.schema.module.queue.queueRequest.ViewQueueRequestsRequest;
import com.omgservers.schema.module.queue.queueRequest.ViewQueueRequestsResponse;
import com.omgservers.service.module.queue.QueueModule;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class FetchQueueOperationImpl implements FetchQueueOperation {

    final QueueModule queueModule;

    @Override
    public Uni<FetchedQueue> execute(Long queueId) {
        // TODO: implement via single module request instead of get and view
        return getQueue(queueId)
                .flatMap(queue -> viewQueueRequests(queueId)
                        .map(queueRequests -> new FetchedQueue(queue, queueRequests)));
    }

    Uni<QueueModel> getQueue(final Long id) {
        final var request = new GetQueueRequest(id);
        return queueModule.getQueueService().execute(request)
                .map(GetQueueResponse::getQueue);
    }

    Uni<List<QueueRequestModel>> viewQueueRequests(final Long queueId) {
        final var request = new ViewQueueRequestsRequest(queueId);
        return queueModule.getQueueService().execute(request)
                .map(ViewQueueRequestsResponse::getQueueRequests);
    }
}
