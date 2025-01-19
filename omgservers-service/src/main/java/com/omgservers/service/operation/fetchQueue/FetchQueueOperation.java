package com.omgservers.service.operation.fetchQueue;

import io.smallrye.mutiny.Uni;

public interface FetchQueueOperation {
    Uni<FetchedQueue> execute(Long queueId);
}
