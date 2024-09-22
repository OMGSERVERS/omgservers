package com.omgservers.service.handler.lobby;

import com.omgservers.schema.module.lobby.DeleteLobbyRequest;
import com.omgservers.service.event.body.module.lobby.LobbyDeletedEventBodyModel;
import com.omgservers.service.factory.system.EventModelFactory;
import com.omgservers.service.handler.lobby.testInterface.LobbyDeletedEventHandlerImplTestInterface;
import com.omgservers.service.module.lobby.impl.service.lobbyService.testInterface.LobbyServiceTestInterface;
import com.omgservers.testDataFactory.TestDataFactory;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@Slf4j
@QuarkusTest
class LobbyDeletedEventHandlerImplTest extends Assertions {

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
        final var project = testDataFactory.getTenantTestDataFactory().createProject(tenant);
        final var version = testDataFactory.getTenantTestDataFactory().createVersion(project);
        final var lobby = testDataFactory.getLobbyTestDataFactory().createLobby(version);

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