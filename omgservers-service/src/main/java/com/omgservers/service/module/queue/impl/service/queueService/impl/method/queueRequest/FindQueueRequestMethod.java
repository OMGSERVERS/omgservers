package com.omgservers.service.module.queue.impl.service.queueService.impl.method.queueRequest;

import com.omgservers.schema.module.queue.queueRequest.FindQueueRequestRequest;
import com.omgservers.schema.module.queue.queueRequest.FindQueueRequestResponse;
import io.smallrye.mutiny.Uni;

public interface FindQueueRequestMethod {
    Uni<FindQueueRequestResponse> execute(FindQueueRequestRequest request);
}
