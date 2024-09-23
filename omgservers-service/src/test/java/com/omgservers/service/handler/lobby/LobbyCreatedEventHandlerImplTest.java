package com.omgservers.service.handler.lobby;

import com.omgservers.service.event.body.module.lobby.LobbyCreatedEventBodyModel;
import com.omgservers.service.factory.system.EventModelFactory;
import com.omgservers.service.handler.lobby.testInterface.LobbyCreatedEventHandlerImplTestInterface;
import com.omgservers.testDataFactory.TestDataFactory;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@Slf4j
@QuarkusTest
class LobbyCreatedEventHandlerImplTest extends Assertions {

    @Inject
    LobbyCreatedEventHandlerImplTestInterface lobbyCreatedEventHandler;

    @Inject
    EventModelFactory eventModelFactory;

    @Inject
    TestDataFactory testDataFactory;

    @Test
    void givenHandler_whenRetry_thenFinished() {
        final var tenant = testDataFactory.getTenantTestDataFactory().createTenant();
        final var tenantProject = testDataFactory.getTenantTestDataFactory().createTenantProject(tenant);
        final var tenantStage = testDataFactory.getTenantTestDataFactory().createStage(tenantProject);
        final var tenantVersion = testDataFactory.getTenantTestDataFactory().createTenantVersion(tenantProject);
        final var tenantDeployment = testDataFactory.getTenantTestDataFactory().createTenantDeployment(tenantStage, tenantVersion);
        final var lobby = testDataFactory.getLobbyTestDataFactory().createLobby(tenantDeployment);

        final var lobbyId = lobby.getId();

        final var eventBody = new LobbyCreatedEventBodyModel(lobbyId);
        final var eventModel = eventModelFactory.create(eventBody);

        lobbyCreatedEventHandler.handle(eventModel);
        log.info("Retry");
        lobbyCreatedEventHandler.handle(eventModel);
    }
}