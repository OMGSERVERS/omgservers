package com.omgservers.service.shard.queue.impl.service.queueService.impl.method.queueRequest;

import com.omgservers.schema.module.queue.queueRequest.SyncQueueRequestRequest;
import com.omgservers.schema.module.queue.queueRequest.SyncQueueRequestResponse;
import io.smallrye.mutiny.Uni;

public interface SyncQueueRequestMethod {
    Uni<SyncQueueRequestResponse> execute(SyncQueueRequestRequest request);
}
