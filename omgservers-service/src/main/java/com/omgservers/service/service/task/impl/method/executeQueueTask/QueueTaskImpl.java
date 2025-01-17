package com.omgservers.service.service.task.impl.method.executeQueueTask;

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
public class QueueTaskImpl {

    final QueueModule queueModule;

    public Uni<Boolean> execute(final Long queueId) {
        return getQueue(queueId)
                .flatMap(queue -> handleQueue(queue)
                        .replaceWith(Boolean.TRUE));
    }

    Uni<QueueModel> getQueue(final Long id) {
        final var request = new GetQueueRequest(id);
        return queueModule.getQueueService().execute(request)
                .map(GetQueueResponse::getQueue);
    }

    Uni<Void> handleQueue(final QueueModel queue) {
        final var queueId = queue.getId();

        return viewQueueRequests(queueId)
                .flatMap(queueRequests -> {
                    if (queueRequests.size() > 0) {
                        // TODO: TBD
                        return Uni.createFrom().voidItem();
                    } else {
                        log.trace("The queue \"{}\" has no requests to process", queueId);
                        return Uni.createFrom().voidItem();
                    }
                });
    }

    Uni<List<QueueRequestModel>> viewQueueRequests(final Long queueId) {
        final var request = new ViewQueueRequestsRequest(queueId);
        return queueModule.getQueueService().execute(request)
                .map(ViewQueueRequestsResponse::getQueueRequests);
    }
}
