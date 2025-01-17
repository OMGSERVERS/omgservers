package com.omgservers.service.module.queue.impl.service.queueService.impl.method.queueRequest;

import com.omgservers.schema.module.queue.queueRequest.ViewQueueRequestsRequest;
import com.omgservers.schema.module.queue.queueRequest.ViewQueueRequestsResponse;
import io.smallrye.mutiny.Uni;

public interface ViewQueueRequestsMethod {
    Uni<ViewQueueRequestsResponse> execute(ViewQueueRequestsRequest request);
}
