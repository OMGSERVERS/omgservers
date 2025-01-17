package com.omgservers.service.module.queue.impl.service.queueService.impl.method.queue;

import com.omgservers.schema.module.queue.queue.GetQueueRequest;
import com.omgservers.schema.module.queue.queue.GetQueueResponse;
import io.smallrye.mutiny.Uni;

public interface GetQueueMethod {
    Uni<GetQueueResponse> execute(GetQueueRequest request);
}
