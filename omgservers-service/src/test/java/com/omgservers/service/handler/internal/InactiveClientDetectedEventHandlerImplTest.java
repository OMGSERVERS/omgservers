package com.omgservers.service.handler.internal;

import com.omgservers.schema.event.body.internal.InactiveClientDetectedEventBodyModel;
import com.omgservers.service.factory.system.EventModelFactory;
import com.omgservers.service.handler.internal.testInterface.InactiveClientDetectedEventHandlerImplTestInterface;
import com.omgservers.service.server.operation.generateId.GenerateIdOperation;
import com.omgservers.testDataFactory.TestDataFactory;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@Slf4j
@QuarkusTest
class InactiveClientDetectedEventHandlerImplTest extends Assertions {

    @Inject
    InactiveClientDetectedEventHandlerImplTestInterface inactiveClientDetectedEventHandler;

    @Inject
    GenerateIdOperation generateIdOperation;

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

        final var eventBody = new InactiveClientDetectedEventBodyModel(clientId);
        final var eventModel = eventModelFactory.create(eventBody);

        inactiveClientDetectedEventHandler.handle(eventModel);
        log.info("Retry");
        inactiveClientDetectedEventHandler.handle(eventModel);
    }
}