package com.omgservers.service.module.queue.impl.service.queueService;

import com.omgservers.BaseTestClass;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@QuarkusTest
class QueueServiceTest extends BaseTestClass {

    @Inject
    QueueService queueService;
}