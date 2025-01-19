package com.omgservers.service.operation.queue;

import io.smallrye.mutiny.Uni;

public interface DeleteQueueRequestsOperation {
    Uni<Void> execute(Long queueId);
}
