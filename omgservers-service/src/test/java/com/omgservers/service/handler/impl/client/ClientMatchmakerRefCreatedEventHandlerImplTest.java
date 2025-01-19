package com.omgservers.service.handler.impl.client;

import com.omgservers.BaseTestClass;
import com.omgservers.service.event.body.module.client.ClientMatchmakerRefCreatedEventBodyModel;
import com.omgservers.service.factory.system.EventModelFactory;
import com.omgservers.service.handler.impl.client.testInterface.ClientMatchmakerRefCreatedEventHandlerImplTestInterface;
import com.omgservers.testDataFactory.TestDataFactory;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
@QuarkusTest
class ClientMatchmakerRefCreatedEventHandlerImplTest extends BaseTestClass {

    @Inject
    ClientMatchmakerRefCreatedEventHandlerImplTestInterface clientMatchmakerRefCreatedEventHandler;

    @Inject
    EventModelFactory eventModelFactory;

    @Inject
    TestDataFactory testDataFactory;

    @Test
    void givenHandler_whenRetry_thenFinished() {
        final var testData = testDataFactory.createDefaultTestData();

        final var client = testData.getClient();
        final var clientMatchmakerRef = testData.getClientMatchmakerRef();

        final var clientId = client.getId();
        final var id = clientMatchmakerRef.getId();

        final var eventBody = new ClientMatchmakerRefCreatedEventBodyModel(clientId, id);
        final var eventModel = eventModelFactory.create(eventBody);

        clientMatchmakerRefCreatedEventHandler.handle(eventModel);
        log.info("Retry");
        clientMatchmakerRefCreatedEventHandler.handle(eventModel);
    }
}