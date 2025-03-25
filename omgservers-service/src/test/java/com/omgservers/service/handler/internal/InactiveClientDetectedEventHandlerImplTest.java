package com.omgservers.service.handler.internal;

import com.omgservers.BaseTestClass;
import com.omgservers.service.event.body.internal.InactiveClientDetectedEventBodyModel;
import com.omgservers.service.factory.system.EventModelFactory;
import com.omgservers.service.handler.internal.testInterface.InactiveClientDetectedEventHandlerImplTestInterface;
import com.omgservers.testDataFactory.TestDataFactory;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
@QuarkusTest
class InactiveClientDetectedEventHandlerImplTest extends BaseTestClass {

    @Inject
    InactiveClientDetectedEventHandlerImplTestInterface inactiveClientDetectedEventHandler;

    @Inject
    EventModelFactory eventModelFactory;

    @Inject
    TestDataFactory testDataFactory;

    @Test
    void givenHandler_whenRetry_thenFinished() {
        final var testData = testDataFactory.createDefaultTestData();

        final var client = testData.getClient();

        final var clientId = client.getId();

        final var eventBody = new InactiveClientDetectedEventBodyModel(clientId);
        final var eventModel = eventModelFactory.create(eventBody);

        inactiveClientDetectedEventHandler.handle(eventModel);
        log.info("Retry");
        inactiveClientDetectedEventHandler.handle(eventModel);
    }
}