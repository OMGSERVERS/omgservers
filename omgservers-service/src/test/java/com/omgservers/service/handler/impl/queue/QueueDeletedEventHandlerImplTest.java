package com.omgservers.service.handler.impl.queue;

import com.omgservers.BaseTestClass;
import com.omgservers.schema.module.queue.queue.DeleteQueueRequest;
import com.omgservers.service.event.body.module.queue.QueueDeletedEventBodyModel;
import com.omgservers.service.factory.system.EventModelFactory;
import com.omgservers.service.handler.impl.queue.testInterface.QueueDeletedEventHandlerImplTestInterface;
import com.omgservers.service.module.queue.impl.service.queueService.testInterface.QueueServiceTestInterface;
import com.omgservers.testDataFactory.TestDataFactory;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
@QuarkusTest
class QueueDeletedEventHandlerImplTest extends BaseTestClass {

    @Inject
    QueueDeletedEventHandlerImplTestInterface queueDeletedEventHandler;

    @Inject
    QueueServiceTestInterface queueService;

    @Inject
    EventModelFactory eventModelFactory;

    @Inject
    TestDataFactory testDataFactory;

    @Test
    void givenHandler_whenRetry_thenFinished() {
        final var testData = testDataFactory.createDefaultTestData();

        final var queueId = testData.getQueue().getId();

        final var deleteQueueRequest = new DeleteQueueRequest(queueId);
        queueService.execute(deleteQueueRequest);

        final var eventBody = new QueueDeletedEventBodyModel(queueId);
        final var eventModel = eventModelFactory.create(eventBody);

        queueDeletedEventHandler.handle(eventModel);
        log.info("Retry");
        queueDeletedEventHandler.handle(eventModel);
    }
}