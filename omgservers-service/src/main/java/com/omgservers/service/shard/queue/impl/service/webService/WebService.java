package com.omgservers.service.shard.queue.impl.service.webService;

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

public interface WebService {

    /*
    Queue
     */

    Uni<GetQueueResponse> execute(GetQueueRequest request);

    Uni<SyncQueueResponse> execute(SyncQueueRequest request);

    Uni<DeleteQueueResponse> execute(DeleteQueueRequest request);

    /*
    QueueRequest
     */

    Uni<GetQueueRequestResponse> execute(GetQueueRequestRequest request);

    Uni<FindQueueRequestResponse> execute(FindQueueRequestRequest request);

    Uni<ViewQueueRequestsResponse> execute(ViewQueueRequestsRequest request);

    Uni<SyncQueueRequestResponse> execute(SyncQueueRequestRequest request);

    Uni<DeleteQueueRequestResponse> execute(DeleteQueueRequestRequest request);
}
