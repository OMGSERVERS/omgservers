package com.omgservers.service.handler.internal;

import com.omgservers.model.event.body.internal.LobbyAssignmentRequestedEventBodyModel;
import com.omgservers.service.factory.system.EventModelFactory;
import com.omgservers.service.handler.internal.testInterface.LobbyAssignmentRequestedEventHandlerImplTestInterface;
import com.omgservers.testDataFactory.TestDataFactory;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@Slf4j
@QuarkusTest
class LobbyAssignmentRequestedEventHandlerImplTest extends Assertions {

    @Inject
    LobbyAssignmentRequestedEventHandlerImplTestInterface lobbyAssignmentRequestedEventHandler;

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
        final var versionLobbyRef = testDataFactory.getTenantTestDataFactory().createVersionLobbyRef(version, lobby);
        final var lobbyRuntime = testDataFactory.getRuntimeTestDataFactory()
                .createLobbyRuntime(tenant, version, lobby);
        final var lobbyRuntimeRef = testDataFactory.getLobbyTestDataFactory()
                .createLobbyRuntimeRef(lobby, lobbyRuntime);
        final var user = testDataFactory.getUserTestDataFactory().createPlayerUser("password");
        final var player = testDataFactory.getUserTestDataFactory().createUserPlayer(user, tenant, stage);
        final var client = testDataFactory.getClientTestDataFactory().createClient(player, tenant, version);

        final var clientId = client.getId();
        final var tenantId = tenant.getId();
        final var versionId = version.getId();

        final var eventBody = new LobbyAssignmentRequestedEventBodyModel(clientId, tenantId, versionId);
        final var eventModel = eventModelFactory.create(eventBody);

        lobbyAssignmentRequestedEventHandler.handle(eventModel);
        log.info("Retry");
        lobbyAssignmentRequestedEventHandler.handle(eventModel);
    }
}