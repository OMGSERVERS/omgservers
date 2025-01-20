package com.omgservers.service.handler.impl.queue;

import com.omgservers.BaseTestClass;
import com.omgservers.service.event.body.module.queue.QueueCreatedEventBodyModel;
import com.omgservers.service.factory.system.EventModelFactory;
import com.omgservers.service.handler.impl.queue.testInterface.QueueCreatedEventHandlerImplTestInterface;
import com.omgservers.testDataFactory.TestDataFactory;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
@QuarkusTest
class QueueCreatedEventHandlerImplTest extends BaseTestClass {

    @Inject
    QueueCreatedEventHandlerImplTestInterface queueCreatedEventHandler;

    @Inject
    EventModelFactory eventModelFactory;

    @Inject
    TestDataFactory testDataFactory;

    @Test
    void givenHandler_whenRetry_thenFinished() {
        final var testData = testDataFactory.createDefaultTestData();

        final var queueId = testData.getQueue().getId();

        final var eventBody = new QueueCreatedEventBodyModel(queueId);
        final var eventModel = eventModelFactory.create(eventBody);

        queueCreatedEventHandler.handle(eventModel);
        log.info("Retry");
        queueCreatedEventHandler.handle(eventModel);
    }
}