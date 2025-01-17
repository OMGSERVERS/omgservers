package com.omgservers.service.module.queue.impl.service.webService.impl;

import com.omgservers.schema.module.queue.queue.DeleteQueueRequest;
import com.omgservers.schema.module.queue.queue.DeleteQueueResponse;
import com.omgservers.schema.module.queue.queue.GetQueueRequest;
import com.omgservers.schema.module.queue.queue.GetQueueResponse;
import com.omgservers.schema.module.queue.queue.SyncQueueRequest;
import com.omgservers.schema.module.queue.queue.SyncQueueResponse;
import com.omgservers.schema.module.queue.queueRequest.DeleteQueueRequestRequest;
import com.omgservers.schema.module.queue.queueRequest.DeleteQueueRequestResponse;
import com.omgservers.schema.module.queue.queueRequest.FindQueueRequestRequest;
import com.omgservers.schema.module.queue.queueRequest.FindQueueRequestResponse;
import com.omgservers.schema.module.queue.queueRequest.GetQueueRequestRequest;
import com.omgservers.schema.module.queue.queueRequest.GetQueueRequestResponse;
import com.omgservers.schema.module.queue.queueRequest.SyncQueueRequestRequest;
import com.omgservers.schema.module.queue.queueRequest.SyncQueueRequestResponse;
import com.omgservers.schema.module.queue.queueRequest.ViewQueueRequestsRequest;
import com.omgservers.schema.module.queue.queueRequest.ViewQueueRequestsResponse;
import com.omgservers.service.module.queue.impl.service.queueService.QueueService;
import com.omgservers.service.module.queue.impl.service.webService.WebService;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class WebServiceImpl implements WebService {

    final QueueService queueService;

    /*
    Queue
     */

    @Override
    public Uni<GetQueueResponse> execute(final GetQueueRequest request) {
        return queueService.execute(request);
    }

    @Override
    public Uni<SyncQueueResponse> execute(final SyncQueueRequest request) {
        return queueService.execute(request);
    }

    @Override
    public Uni<DeleteQueueResponse> execute(final DeleteQueueRequest request) {
        return queueService.execute(request);
    }

    /*
    QueueRequest
     */

    @Override
    public Uni<GetQueueRequestResponse> execute(final GetQueueRequestRequest request) {
        return queueService.execute(request);
    }

    @Override
    public Uni<FindQueueRequestResponse> execute(final FindQueueRequestRequest request) {
        return queueService.execute(request);
    }

    @Override
    public Uni<ViewQueueRequestsResponse> execute(final ViewQueueRequestsRequest request) {
        return queueService.execute(request);
    }

    @Override
    public Uni<SyncQueueRequestResponse> execute(final SyncQueueRequestRequest request) {
        return queueService.execute(request);
    }

    @Override
    public Uni<DeleteQueueRequestResponse> execute(final DeleteQueueRequestRequest request) {
        return queueService.execute(request);
    }
}
