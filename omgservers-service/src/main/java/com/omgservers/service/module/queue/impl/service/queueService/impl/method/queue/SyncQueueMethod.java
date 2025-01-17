package com.omgservers.service.module.queue.impl.service.queueService.impl.method.queue;

import com.omgservers.schema.module.queue.queue.SyncQueueRequest;
import com.omgservers.schema.module.queue.queue.SyncQueueResponse;
import io.smallrye.mutiny.Uni;

public interface SyncQueueMethod {
    Uni<SyncQueueResponse> execute(SyncQueueRequest request);
}
