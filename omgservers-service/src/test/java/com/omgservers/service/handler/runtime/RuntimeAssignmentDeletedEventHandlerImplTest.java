package com.omgservers.service.handler.runtime;

import com.omgservers.schema.module.runtime.DeleteRuntimeAssignmentRequest;
import com.omgservers.service.event.body.module.runtime.RuntimeAssignmentDeletedEventBodyModel;
import com.omgservers.service.factory.system.EventModelFactory;
import com.omgservers.service.handler.runtime.testInterface.RuntimeAssignmentDeletedEventHandlerImplTestInterface;
import com.omgservers.service.module.runtime.impl.service.runtimeService.testInterface.RuntimeServiceTestInterface;
import com.omgservers.testDataFactory.TestDataFactory;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@Slf4j
@QuarkusTest
class RuntimeAssignmentDeletedEventHandlerImplTest extends Assertions {

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
        final var defaultPool = testDataFactory.getPoolTestDataFactory().createDefaultPool();
        final var tenant = testDataFactory.getTenantTestDataFactory().createTenant();
        final var project = testDataFactory.getTenantTestDataFactory().createProject(tenant);
        final var stage = testDataFactory.getTenantTestDataFactory().createStage(project);
        final var version = testDataFactory.getTenantTestDataFactory().createVersion(project);
        final var lobby = testDataFactory.getLobbyTestDataFactory().createLobby(version);
        final var lobbyRuntime = testDataFactory.getRuntimeTestDataFactory()
                .createLobbyRuntime(tenant, version, lobby);
        final var user = testDataFactory.getUserTestDataFactory().createPlayerUser("password");
        final var player = testDataFactory.getUserTestDataFactory().createUserPlayer(user, tenant, stage);
        final var client = testDataFactory.getClientTestDataFactory().createClient(player, tenant, version);
        final var runtimeAssignment = testDataFactory.getRuntimeTestDataFactory()
                .createRuntimeAssignment(lobbyRuntime, client);

        final var runtimeId = runtimeAssignment.getRuntimeId();
        final var id = runtimeAssignment.getId();

        final var deleteRuntimeAssignmentRequest = new DeleteRuntimeAssignmentRequest(runtimeId, id);
        runtimeService.deleteRuntimeAssignment(deleteRuntimeAssignmentRequest);

        final var eventBody = new RuntimeAssignmentDeletedEventBodyModel(runtimeId, id);
        final var eventModel = eventModelFactory.create(eventBody);

        runtimeAssignmentDeletedEventHandler.handle(eventModel);
        log.info("Retry");
        runtimeAssignmentDeletedEventHandler.handle(eventModel);
    }
}