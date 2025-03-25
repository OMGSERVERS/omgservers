package com.omgservers.service.handler.pool;

import com.omgservers.BaseTestClass;
import com.omgservers.service.event.body.module.pool.PoolCreatedEventBodyModel;
import com.omgservers.service.factory.system.EventModelFactory;
import com.omgservers.service.handler.pool.testInterface.PoolCreatedEventHandlerImplTestInterface;
import com.omgservers.testDataFactory.TestDataFactory;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
@QuarkusTest
class PoolCreatedEventHandlerImplTest extends BaseTestClass {

    @Inject
    PoolCreatedEventHandlerImplTestInterface poolCreatedEventHandlerImplTestInterface;

    @Inject
    EventModelFactory eventModelFactory;

    @Inject
    TestDataFactory testDataFactory;

    @Test
    void givenHandler_whenRetry_thenFinished() {
        final var pool = testDataFactory.getPoolTestDataFactory().createDefaultPool();

        final var poolId = pool.getId();

        final var eventBody = new PoolCreatedEventBodyModel(poolId);
        final var eventModel = eventModelFactory.create(eventBody);

        poolCreatedEventHandlerImplTestInterface.handle(eventModel);
        log.info("Retry");
        poolCreatedEventHandlerImplTestInterface.handle(eventModel);
    }
}