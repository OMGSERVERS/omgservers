package com.omgservers.service.handler.client;

import com.omgservers.BaseTestClass;
import com.omgservers.service.event.body.module.client.ClientRuntimeRefCreatedEventBodyModel;
import com.omgservers.service.factory.system.EventModelFactory;
import com.omgservers.service.handler.client.testInterface.ClientRuntimeRefCreatedEventHandlerImplTestInterface;
import com.omgservers.testDataFactory.TestDataFactory;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
@QuarkusTest
class ClientRuntimeRefCreatedEventHandlerImplTest extends BaseTestClass {

    @Inject
    ClientRuntimeRefCreatedEventHandlerImplTestInterface clientRuntimeRefCreatedEventHandler;

    @Inject
    EventModelFactory eventModelFactory;

    @Inject
    TestDataFactory testDataFactory;

    @Test
    void givenHandler_whenRetry_thenFinished() {
        final var testData = testDataFactory.createDefaultTestData();

        final var clientRuntimeRef = testData.getClientRuntimeRef();

        final var clientId = clientRuntimeRef.getClientId();
        final var id = clientRuntimeRef.getId();

        final var eventBody = new ClientRuntimeRefCreatedEventBodyModel(clientId, id);
        final var eventModel = eventModelFactory.create(eventBody);

        clientRuntimeRefCreatedEventHandler.handle(eventModel);
        log.info("Retry");
        clientRuntimeRefCreatedEventHandler.handle(eventModel);
    }
}