package com.omgservers.service.handler.impl.lobby;

import com.omgservers.BaseTestClass;
import com.omgservers.schema.module.lobby.DeleteLobbyRequest;
import com.omgservers.service.event.body.module.lobby.LobbyDeletedEventBodyModel;
import com.omgservers.service.factory.system.EventModelFactory;
import com.omgservers.service.handler.impl.lobby.testInterface.LobbyDeletedEventHandlerImplTestInterface;
import com.omgservers.service.shard.lobby.impl.service.lobbyService.testInterface.LobbyServiceTestInterface;
import com.omgservers.testDataFactory.TestDataFactory;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
@QuarkusTest
class LobbyDeletedEventHandlerImplTest extends BaseTestClass {

    @Inject
    LobbyDeletedEventHandlerImplTestInterface lobbyDeletedEventHandler;

    @Inject
    LobbyServiceTestInterface lobbyService;

    @Inject
    EventModelFactory eventModelFactory;

    @Inject
    TestDataFactory testDataFactory;

    @Test
    void givenHandler_whenRetry_thenFinished() {
        final var tenant = testDataFactory.getTenantTestDataFactory().createTenant();
        final var project = testDataFactory.getTenantTestDataFactory().createTenantProject(tenant);
        final var stage = testDataFactory.getTenantTestDataFactory().createStage(project);
        final var version = testDataFactory.getTenantTestDataFactory().createTenantVersion(project);
        final var tenantDeployment = testDataFactory.getTenantTestDataFactory().createTenantDeployment(stage, version);
        final var lobby = testDataFactory.getLobbyTestDataFactory().createLobby(tenantDeployment);

        final var lobbyId = lobby.getId();

        final var deleteLobbyRequest = new DeleteLobbyRequest(lobbyId);
        lobbyService.deleteLobby(deleteLobbyRequest);

        final var eventBody = new LobbyDeletedEventBodyModel(lobbyId);
        final var eventModel = eventModelFactory.create(eventBody);

        lobbyDeletedEventHandler.handle(eventModel);
        log.info("Retry");
        lobbyDeletedEventHandler.handle(eventModel);
    }
}