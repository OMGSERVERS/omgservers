package com.omgservers.service.handler.runtime;

import com.omgservers.BaseTestClass;
import com.omgservers.schema.module.runtime.runtimeAssignment.DeleteRuntimeAssignmentRequest;
import com.omgservers.service.event.body.module.runtime.RuntimeAssignmentDeletedEventBodyModel;
import com.omgservers.service.factory.system.EventModelFactory;
import com.omgservers.service.handler.runtime.testInterface.RuntimeAssignmentDeletedEventHandlerImplTestInterface;
import com.omgservers.service.shard.runtime.service.testInterface.RuntimeServiceTestInterface;
import com.omgservers.testDataFactory.TestDataFactory;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
@QuarkusTest
class RuntimeAssignmentDeletedEventHandlerImplTest extends BaseTestClass {

    @Inject
    RuntimeAssignmentDeletedEventHandlerImplTestInterface runtimeAssignmentDeletedEventHandler;

    @Inject
    RuntimeServiceTestInterface runtimeService;

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

        final var deleteRuntimeAssignmentRequest = new DeleteRuntimeAssignmentRequest(runtimeId, id);
        runtimeService.execute(deleteRuntimeAssignmentRequest);

        final var eventBody = new RuntimeAssignmentDeletedEventBodyModel(runtimeId, id);
        final var eventModel = eventModelFactory.create(eventBody);

        runtimeAssignmentDeletedEventHandler.handle(eventModel);
        log.info("Retry");
        runtimeAssignmentDeletedEventHandler.handle(eventModel);
    }
}