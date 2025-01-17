package com.omgservers.service.module.queue;

import com.omgservers.service.module.queue.impl.service.queueService.QueueService;

public interface QueueModule {

    QueueService getQueueService();
}
