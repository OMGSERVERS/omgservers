package com.omgservers.service.handler.internal;

import com.omgservers.BaseTestClass;
import com.omgservers.service.event.body.internal.InactiveRuntimeDetectedEventBodyModel;
import com.omgservers.service.factory.system.EventModelFactory;
import com.omgservers.service.handler.internal.testInterface.InactiveRuntimeDetectedEventHandlerImplTestInterface;
import com.omgservers.testDataFactory.TestDataFactory;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
@QuarkusTest
class InactiveRuntimeDetectedEventHandlerImplTest extends BaseTestClass {

    @Inject
    InactiveRuntimeDetectedEventHandlerImplTestInterface inactiveRuntimeDetectedEventHandler;

    @Inject
    EventModelFactory eventModelFactory;

    @Inject
    TestDataFactory testDataFactory;

    @Test
    void givenHandler_whenRetry_thenFinished() {
        final var testData = testDataFactory.createDefaultTestData();

        final var lobbyRuntime = testData.getLobbyRuntime();

        final var runtimeId = lobbyRuntime.getId();

        final var eventBody = new InactiveRuntimeDetectedEventBodyModel(runtimeId);
        final var eventModel = eventModelFactory.create(eventBody);

        inactiveRuntimeDetectedEventHandler.handle(eventModel);
        log.info("Retry");
        inactiveRuntimeDetectedEventHandler.handle(eventModel);
    }
}