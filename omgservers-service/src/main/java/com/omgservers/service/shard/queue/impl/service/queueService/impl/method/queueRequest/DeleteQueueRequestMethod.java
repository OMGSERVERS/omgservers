package com.omgservers.service.shard.queue.impl.service.queueService.impl.method.queueRequest;

import com.omgservers.schema.module.queue.queueRequest.DeleteQueueRequestRequest;
import com.omgservers.schema.module.queue.queueRequest.DeleteQueueRequestResponse;
import io.smallrye.mutiny.Uni;

public interface DeleteQueueRequestMethod {
    Uni<DeleteQueueRequestResponse> execute(DeleteQueueRequestRequest request);
}
