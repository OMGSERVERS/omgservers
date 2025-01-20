package com.omgservers.service.shard.alias.impl.service.aliasService;

import com.omgservers.BaseTestClass;
import com.omgservers.service.shard.queue.impl.service.queueService.QueueService;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@QuarkusTest
class AliasServiceTest extends BaseTestClass {

    @Inject
    QueueService queueService;
}