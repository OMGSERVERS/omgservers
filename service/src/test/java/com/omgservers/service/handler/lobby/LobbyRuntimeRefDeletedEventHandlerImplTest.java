package com.omgservers.service.handler.lobby;

import com.omgservers.model.dto.lobby.DeleteLobbyRuntimeRefRequest;
import com.omgservers.model.event.body.module.lobby.LobbyRuntimeRefDeletedEventBodyModel;
import com.omgservers.service.factory.system.EventModelFactory;
import com.omgservers.service.module.lobby.impl.service.lobbyService.testInterface.LobbyServiceTestInterface;
import com.omgservers.testDataFactory.TestDataFactory;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@Slf4j
@QuarkusTest
class LobbyRuntimeRefDeletedEventHandlerImplTest extends Assertions {

    @Inject
    LobbyRuntimeRefDeletedEventHandlerImpl lobbyRuntimeRefDeletedEventHandler;

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
        final var stage = testDataFactory.getTenantTestDataFactory().createStage(project);
        final var version = testDataFactory.getTenantTestDataFactory().createVersion(stage);
        final var lobby = testDataFactory.getLobbyTestDataFactory().createLobby(version);
        final var lobbyRuntime = testDataFactory.getRuntimeTestDataFactory()
                .createLobbyRuntime(tenant, version, lobby);
        final var lobbyRuntimeRef = testDataFactory.getLobbyTestDataFactory()
                .createLobbyRuntimeRef(lobby, lobbyRuntime);

        final var lobbyId = lobbyRuntimeRef.getLobbyId();
        final var id = lobbyRuntimeRef.getId();

        final var deleteLobbyRuntimeRefRequest = new DeleteLobbyRuntimeRefRequest(lobbyId, id);
        lobbyService.deleteLobbyRuntimeRef(deleteLobbyRuntimeRefRequest);

        final var eventBody = new LobbyRuntimeRefDeletedEventBodyModel(lobbyId, id);
        final var eventModel = eventModelFactory.create(eventBody);

        lobbyRuntimeRefDeletedEventHandler.handle(eventModel);
        log.info("Retry");
        lobbyRuntimeRefDeletedEventHandler.handle(eventModel);
    }
}