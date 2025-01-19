package com.omgservers.service.operation.queue;

import io.smallrye.mutiny.Uni;

public interface FetchQueueOperation {
    Uni<FetchedQueue> execute(Long queueId);
}
