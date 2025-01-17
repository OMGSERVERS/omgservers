package com.omgservers.service.module.queue.impl;

import com.omgservers.service.module.queue.QueueModule;
import com.omgservers.service.module.queue.impl.service.queueService.QueueService;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class QueueModuleImpl implements QueueModule {

    final QueueService queueService;
}
