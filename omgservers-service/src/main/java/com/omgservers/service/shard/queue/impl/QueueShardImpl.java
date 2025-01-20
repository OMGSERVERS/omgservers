package com.omgservers.service.shard.queue.impl;

import com.omgservers.service.shard.queue.QueueShard;
import com.omgservers.service.shard.queue.impl.service.queueService.QueueService;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class QueueShardImpl implements QueueShard {

    final QueueService queueService;
}
