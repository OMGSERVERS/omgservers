package com.omgservers.service.handler.impl.internal;

import com.omgservers.BaseTestClass;
import com.omgservers.service.event.body.internal.LobbyAssignmentRequestedEventBodyModel;
import com.omgservers.service.factory.system.EventModelFactory;
import com.omgservers.service.handler.impl.internal.testInterface.LobbyAssignmentRequestedEventHandlerImplTestInterface;
import com.omgservers.testDataFactory.TestDataFactory;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
@QuarkusTest
class LobbyAssignmentRequestedEventHandlerImplTest extends BaseTestClass {

    @Inject
    LobbyAssignmentRequestedEventHandlerImplTestInterface lobbyAssignmentRequestedEventHandler;

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
        final var tenantDeployment = testDataFactory.getTenantTestDataFactory()
                .createTenantDeployment(tenantStage, tenantVersion);
        final var lobby = testDataFactory.getLobbyTestDataFactory().createLobby(tenantDeployment);
        final var tenantLobbyRef = testDataFactory.getTenantTestDataFactory()
                .createTenantLobbyRef(tenantDeployment, lobby);
        final var lobbyRuntime = testDataFactory.getRuntimeTestDataFactory()
                .createLobbyRuntime(tenant, tenantDeployment, lobby);
        final var lobbyRuntimeRef = testDataFactory.getLobbyTestDataFactory()
                .createLobbyRuntimeRef(lobby, lobbyRuntime);
        final var user = testDataFactory.getUserTestDataFactory().createPlayerUser("password");
        final var player = testDataFactory.getUserTestDataFactory().createUserPlayer(user,
                tenant,
                tenantStage);
        final var client = testDataFactory.getClientTestDataFactory().createClient(player,
                tenant,
                tenantDeployment);

        final var clientId = client.getId();
        final var tenantId = tenant.getId();
        final var tenantDeploymentId = tenantDeployment.getId();

        final var eventBody = new LobbyAssignmentRequestedEventBodyModel(clientId, tenantId, tenantDeploymentId);
        final var eventModel = eventModelFactory.create(eventBody);

        lobbyAssignmentRequestedEventHandler.handle(eventModel);
        log.info("Retry");
        lobbyAssignmentRequestedEventHandler.handle(eventModel);
    }
}