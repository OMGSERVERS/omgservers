package com.omgservers.service.module.queue.impl.service.queueService.impl.method.queue;

import com.omgservers.schema.module.queue.queue.DeleteQueueRequest;
import com.omgservers.schema.module.queue.queue.DeleteQueueResponse;
import io.smallrye.mutiny.Uni;

public interface DeleteQueueMethod {
    Uni<DeleteQueueResponse> execute(DeleteQueueRequest request);
}
