package com.omgservers.service.handler.operation;

import io.smallrye.mutiny.Uni;

public interface DeleteQueueRequestsOperation {
    Uni<Void> execute(Long queueId);
}
