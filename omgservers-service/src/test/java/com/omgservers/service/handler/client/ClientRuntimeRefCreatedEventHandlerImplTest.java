package com.omgservers.service.handler.client;

import com.omgservers.service.event.body.module.client.ClientRuntimeRefCreatedEventBodyModel;
import com.omgservers.service.factory.system.EventModelFactory;
import com.omgservers.service.handler.client.testInterface.ClientRuntimeRefCreatedEventHandlerImplTestInterface;
import com.omgservers.testDataFactory.TestDataFactory;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@Slf4j
@QuarkusTest
class ClientRuntimeRefCreatedEventHandlerImplTest extends Assertions {

    @Inject
    ClientRuntimeRefCreatedEventHandlerImplTestInterface clientRuntimeRefCreatedEventHandler;

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
        final var user = testDataFactory.getUserTestDataFactory().createPlayerUser("password");
        final var player = testDataFactory.getUserTestDataFactory().createUserPlayer(user, tenant, stage);
        final var client = testDataFactory.getClientTestDataFactory().createClient(player, tenant, version);
        final var clientRuntimeRef = testDataFactory.getClientTestDataFactory()
                .createClientRuntimeRef(client, lobbyRuntime);

        final var clientId = client.getId();
        final var id = clientRuntimeRef.getId();

        final var eventBody = new ClientRuntimeRefCreatedEventBodyModel(clientId, id);
        final var eventModel = eventModelFactory.create(eventBody);

        clientRuntimeRefCreatedEventHandler.handle(eventModel);
        log.info("Retry");
        clientRuntimeRefCreatedEventHandler.handle(eventModel);
    }
}