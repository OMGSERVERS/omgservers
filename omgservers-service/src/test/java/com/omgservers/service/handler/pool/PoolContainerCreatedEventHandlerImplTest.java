package com.omgservers.service.handler.pool;

import com.omgservers.BaseTestClass;
import com.omgservers.service.event.body.module.pool.PoolContainerCreatedEventBodyModel;
import com.omgservers.service.factory.system.EventModelFactory;
import com.omgservers.service.handler.pool.testInterface.PoolContainerCreatedEventHandlerImplTestInterface;
import com.omgservers.testDataFactory.TestDataFactory;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

@Slf4j
@QuarkusTest
class PoolContainerCreatedEventHandlerImplTest extends BaseTestClass {

    @Inject
    PoolContainerCreatedEventHandlerImplTestInterface poolContainerCreatedEventHandler;

    @Inject
    EventModelFactory eventModelFactory;

    @Inject
    TestDataFactory testDataFactory;

    @Test
    @Disabled
    void givenHandler_whenRetry_thenFinished() {
        final var testData = testDataFactory.createDefaultTestData();

        final var poolContainer = testData.getLobbyPoolContainer();

        final var poolId = poolContainer.getPoolId();
        final var id = poolContainer.getId();

        final var eventBody = new PoolContainerCreatedEventBodyModel(poolId, id);
        final var eventModel = eventModelFactory.create(eventBody);

        poolContainerCreatedEventHandler.handle(eventModel);
        log.info("Retry");
        poolContainerCreatedEventHandler.handle(eventModel);
    }
}