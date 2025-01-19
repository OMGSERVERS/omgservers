package com.omgservers.service.module.queue.impl.service.queueService;

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
import io.smallrye.mutiny.Uni;
import jakarta.validation.Valid;

public interface QueueService {

    /*
    Queue
     */

    Uni<GetQueueResponse> execute(@Valid GetQueueRequest request);

    Uni<SyncQueueResponse> execute(@Valid SyncQueueRequest request);

    Uni<SyncQueueResponse> executeWithIdempotency(@Valid SyncQueueRequest request);

    Uni<DeleteQueueResponse> execute(@Valid DeleteQueueRequest request);

    /*
    QueueRequest
     */

    Uni<GetQueueRequestResponse> execute(@Valid GetQueueRequestRequest request);

    Uni<FindQueueRequestResponse> execute(@Valid FindQueueRequestRequest request);

    Uni<ViewQueueRequestsResponse> execute(@Valid ViewQueueRequestsRequest request);

    Uni<SyncQueueRequestResponse> execute(@Valid SyncQueueRequestRequest request);

    Uni<SyncQueueRequestResponse> executeWithIdempotency(@Valid SyncQueueRequestRequest request);

    Uni<DeleteQueueRequestResponse> execute(@Valid DeleteQueueRequestRequest request);
}
