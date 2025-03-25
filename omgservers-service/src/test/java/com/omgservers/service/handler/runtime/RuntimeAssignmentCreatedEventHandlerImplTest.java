package com.omgservers.service.handler.runtime;

import com.omgservers.BaseTestClass;
import com.omgservers.service.event.body.module.runtime.RuntimeAssignmentCreatedEventBodyModel;
import com.omgservers.service.factory.system.EventModelFactory;
import com.omgservers.service.handler.runtime.testInterface.RuntimeAssignmentCreatedEventHandlerImplTestInterface;
import com.omgservers.testDataFactory.TestDataFactory;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
@QuarkusTest
class RuntimeAssignmentCreatedEventHandlerImplTest extends BaseTestClass {

    @Inject
    RuntimeAssignmentCreatedEventHandlerImplTestInterface runtimeAssignmentCreatedEventHandler;

    @Inject
    EventModelFactory eventModelFactory;

    @Inject
    TestDataFactory testDataFactory;

    @Test
    void givenHandler_whenRetry_thenFinished() {
        final var testData = testDataFactory.createDefaultTestData();

        final var lobbyRuntimeAssignment = testData.getLobbyRuntimeAssignment();

        final var runtimeId = lobbyRuntimeAssignment.getRuntimeId();
        final var id = lobbyRuntimeAssignment.getId();

        final var eventBody = new RuntimeAssignmentCreatedEventBodyModel(runtimeId, id);
        final var eventModel = eventModelFactory.create(eventBody);

        runtimeAssignmentCreatedEventHandler.handle(eventModel);
        log.info("Retry");
        runtimeAssignmentCreatedEventHandler.handle(eventModel);
    }
}