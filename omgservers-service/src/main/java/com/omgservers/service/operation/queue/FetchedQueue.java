package com.omgservers.service.operation.queue;

import com.omgservers.schema.model.queue.QueueModel;
import com.omgservers.schema.model.queueRequest.QueueRequestModel;

import java.util.List;

public record FetchedQueue(
        QueueModel queue,
        List<QueueRequestModel> queueRequests) {
}
