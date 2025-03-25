package com.omgservers.service.handler.client;

import com.omgservers.BaseTestClass;
import com.omgservers.service.event.body.module.client.ClientCreatedEventBodyModel;
import com.omgservers.service.factory.system.EventModelFactory;
import com.omgservers.service.handler.client.testInterface.ClientCreatedEventHandlerImplTestInterface;
import com.omgservers.testDataFactory.TestDataFactory;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
@QuarkusTest
class ClientCreatedEventHandlerImplTest extends BaseTestClass {

    @Inject
    ClientCreatedEventHandlerImplTestInterface clientCreatedEventHandler;

    @Inject
    EventModelFactory eventModelFactory;

    @Inject
    TestDataFactory testDataFactory;

    @Test
    void givenHandler_whenRetry_thenFinished() {
        final var testData = testDataFactory.createDefaultTestData();
        final var client = testData.getClient();

        final var eventBody = new ClientCreatedEventBodyModel(client.getId());
        final var eventModel = eventModelFactory.create(eventBody);

        clientCreatedEventHandler.handle(eventModel);
        log.info("Retry");
        clientCreatedEventHandler.handle(eventModel);
    }
}