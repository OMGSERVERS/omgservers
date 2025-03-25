package com.omgservers.service.handler.runtime;

import com.omgservers.BaseTestClass;
import com.omgservers.service.event.body.module.runtime.RuntimeCreatedEventBodyModel;
import com.omgservers.service.factory.system.EventModelFactory;
import com.omgservers.service.handler.runtime.testInterface.RuntimeCreatedEventHandlerImplTestInterface;
import com.omgservers.testDataFactory.TestDataFactory;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
@QuarkusTest
class RuntimeCreatedEventHandlerImplTest extends BaseTestClass {

    @Inject
    RuntimeCreatedEventHandlerImplTestInterface runtimeCreatedEventHandler;

    @Inject
    EventModelFactory eventModelFactory;

    @Inject
    TestDataFactory testDataFactory;

    @Test
    void givenHandler_whenRetry_thenFinished() {
        final var testData = testDataFactory.createDefaultTestData();

        final var lobbyRuntime = testData.getLobbyRuntime();

        final var runtimeId = lobbyRuntime.getId();

        final var eventBody = new RuntimeCreatedEventBodyModel(runtimeId);
        final var eventModel = eventModelFactory.create(eventBody);

        runtimeCreatedEventHandler.handle(eventModel);
        log.info("Retry");
        runtimeCreatedEventHandler.handle(eventModel);
    }
}