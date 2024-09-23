package com.omgservers.service.handler.internal;

import com.omgservers.service.event.body.internal.InactiveClientDetectedEventBodyModel;
import com.omgservers.service.factory.system.EventModelFactory;
import com.omgservers.service.handler.internal.testInterface.InactiveClientDetectedEventHandlerImplTestInterface;
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
    EventModelFactory eventModelFactory;

    @Inject
    TestDataFactory testDataFactory;

    @Test
    void givenHandler_whenRetry_thenFinished() {
        final var tenant = testDataFactory.getTenantTestDataFactory().createTenant();
        final var tenantProject = testDataFactory.getTenantTestDataFactory()
                .createTenantProject(tenant);
        final var tenantStage = testDataFactory.getTenantTestDataFactory().createStage(tenantProject);
        final var tenantVersion = testDataFactory.getTenantTestDataFactory()
                .createTenantVersion(tenantProject);
        final var tenantDeployment = testDataFactory.getTenantTestDataFactory()
                .createTenantDeployment(tenantStage, tenantVersion);
        final var lobby = testDataFactory.getLobbyTestDataFactory().createLobby(tenantDeployment);
        final var lobbyRuntime = testDataFactory.getRuntimeTestDataFactory()
                .createLobbyRuntime(tenant, tenantDeployment, lobby);
        final var user = testDataFactory.getUserTestDataFactory().createPlayerUser("password");
        final var player = testDataFactory.getUserTestDataFactory().createUserPlayer(user, tenant, tenantStage);
        final var client = testDataFactory.getClientTestDataFactory().createClient(player,
                tenant,
                tenantDeployment);
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