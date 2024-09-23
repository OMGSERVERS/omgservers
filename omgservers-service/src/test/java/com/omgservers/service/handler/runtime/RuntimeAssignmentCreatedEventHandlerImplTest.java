package com.omgservers.service.handler.runtime;

import com.omgservers.service.event.body.module.runtime.RuntimeAssignmentCreatedEventBodyModel;
import com.omgservers.service.factory.system.EventModelFactory;
import com.omgservers.service.handler.runtime.testInterface.RuntimeAssignmentCreatedEventHandlerImplTestInterface;
import com.omgservers.testDataFactory.TestDataFactory;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@Slf4j
@QuarkusTest
class RuntimeAssignmentCreatedEventHandlerImplTest extends Assertions {

    @Inject
    RuntimeAssignmentCreatedEventHandlerImplTestInterface runtimeAssignmentCreatedEventHandler;

    @Inject
    EventModelFactory eventModelFactory;

    @Inject
    TestDataFactory testDataFactory;

    @Test
    void givenHandler_whenRetry_thenFinished() {
        final var defaultPool = testDataFactory.getPoolTestDataFactory().createDefaultPool();
        final var tenant = testDataFactory.getTenantTestDataFactory().createTenant();
        final var project = testDataFactory.getTenantTestDataFactory().createTenantProject(tenant);
        final var stage = testDataFactory.getTenantTestDataFactory().createStage(project);
        final var version = testDataFactory.getTenantTestDataFactory().createTenantVersion(project);
        final var tenantDeployment = testDataFactory.getTenantTestDataFactory().createTenantDeployment(stage, version);
        final var lobby = testDataFactory.getLobbyTestDataFactory().createLobby(tenantDeployment);
        final var lobbyRuntime = testDataFactory.getRuntimeTestDataFactory()
                .createLobbyRuntime(tenant, tenantDeployment, lobby);
        final var user = testDataFactory.getUserTestDataFactory().createPlayerUser("password");
        final var player = testDataFactory.getUserTestDataFactory().createUserPlayer(user, tenant, stage);
        final var client = testDataFactory.getClientTestDataFactory().createClient(player, tenant, tenantDeployment);
        final var runtimeAssignment = testDataFactory.getRuntimeTestDataFactory()
                .createRuntimeAssignment(lobbyRuntime, client);

        final var runtimeId = runtimeAssignment.getRuntimeId();
        final var id = runtimeAssignment.getId();

        final var eventBody = new RuntimeAssignmentCreatedEventBodyModel(runtimeId, id);
        final var eventModel = eventModelFactory.create(eventBody);

        runtimeAssignmentCreatedEventHandler.handle(eventModel);
        log.info("Retry");
        runtimeAssignmentCreatedEventHandler.handle(eventModel);
    }
}