package com.omgservers.service.handler.lobby;

import com.omgservers.BaseTestClass;
import com.omgservers.schema.module.lobby.DeleteLobbyRequest;
import com.omgservers.service.event.body.module.lobby.LobbyDeletedEventBodyModel;
import com.omgservers.service.factory.system.EventModelFactory;
import com.omgservers.service.handler.lobby.testInterface.LobbyDeletedEventHandlerImplTestInterface;
import com.omgservers.service.shard.lobby.service.testInterface.LobbyServiceTestInterface;
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
        final var testData = testDataFactory.createDefaultTestData();

        final var lobby = testData.getLobby();

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