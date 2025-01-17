package com.omgservers.service.module.queue.impl.service.queueService.impl.method.queueRequest;

import com.omgservers.schema.module.queue.queueRequest.GetQueueRequestRequest;
import com.omgservers.schema.module.queue.queueRequest.GetQueueRequestResponse;
import io.smallrye.mutiny.Uni;

public interface GetQueueRequestMethod {
    Uni<GetQueueRequestResponse> execute(GetQueueRequestRequest request);
}
