package com.omgservers.service.handler.internal;

import com.omgservers.schema.event.body.internal.InactiveRuntimeDetectedEventBodyModel;
import com.omgservers.service.factory.system.EventModelFactory;
import com.omgservers.service.handler.internal.testInterface.InactiveRuntimeDetectedEventHandlerImplTestInterface;
import com.omgservers.testDataFactory.TestDataFactory;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@Slf4j
@QuarkusTest
class InactiveRuntimeDetectedEventHandlerImplTest extends Assertions {

    @Inject
    InactiveRuntimeDetectedEventHandlerImplTestInterface inactiveRuntimeDetectedEventHandler;

    @Inject
    EventModelFactory eventModelFactory;

    @Inject
    TestDataFactory testDataFactory;

    @Test
    void givenHandler_whenRetry_thenFinished() {
        final var tenant = testDataFactory.getTenantTestDataFactory().createTenant();
        final var project = testDataFactory.getTenantTestDataFactory().createProject(tenant);
        final var stage = testDataFactory.getTenantTestDataFactory().createStage(project);
        final var version = testDataFactory.getTenantTestDataFactory().createVersion(stage);
        final var lobby = testDataFactory.getLobbyTestDataFactory().createLobby(version);
        final var lobbyRuntime = testDataFactory.getRuntimeTestDataFactory()
                .createLobbyRuntime(tenant, version, lobby);

        final var runtimeId = lobbyRuntime.getId();

        final var eventBody = new InactiveRuntimeDetectedEventBodyModel(runtimeId);
        final var eventModel = eventModelFactory.create(eventBody);

        inactiveRuntimeDetectedEventHandler.handle(eventModel);
        log.info("Retry");
        inactiveRuntimeDetectedEventHandler.handle(eventModel);
    }
}