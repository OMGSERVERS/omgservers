package com.omgservers.service.shard.queue;

import com.omgservers.service.shard.queue.impl.service.queueService.QueueService;

public interface QueueShard {

    QueueService getQueueService();
}
