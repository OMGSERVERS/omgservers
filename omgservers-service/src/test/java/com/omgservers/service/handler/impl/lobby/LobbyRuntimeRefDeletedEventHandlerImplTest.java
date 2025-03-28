package com.omgservers.service.handler.impl.lobby;

import com.omgservers.BaseTestClass;
import com.omgservers.schema.module.lobby.DeleteLobbyRuntimeRefRequest;
import com.omgservers.service.event.body.module.lobby.LobbyRuntimeRefDeletedEventBodyModel;
import com.omgservers.service.factory.system.EventModelFactory;
import com.omgservers.service.shard.lobby.impl.service.lobbyService.testInterface.LobbyServiceTestInterface;
import com.omgservers.testDataFactory.TestDataFactory;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
@QuarkusTest
class LobbyRuntimeRefDeletedEventHandlerImplTest extends BaseTestClass {

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
        final var project = testDataFactory.getTenantTestDataFactory().createTenantProject(tenant);
        final var stage = testDataFactory.getTenantTestDataFactory().createStage(project);
        final var version = testDataFactory.getTenantTestDataFactory().createTenantVersion(project);
        final var tenantDeployment = testDataFactory.getTenantTestDataFactory().createTenantDeployment(stage, version);
        final var lobby = testDataFactory.getLobbyTestDataFactory().createLobby(tenantDeployment);
        final var lobbyRuntime = testDataFactory.getRuntimeTestDataFactory()
                .createLobbyRuntime(tenant, tenantDeployment, lobby);
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