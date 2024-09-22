package com.omgservers.service.handler.tenant;

import com.omgservers.service.event.body.module.tenant.TenantLobbyRefCreatedEventBodyModel;
import com.omgservers.service.factory.system.EventModelFactory;
import com.omgservers.service.handler.tenant.testInterface.VersionLobbyRefCreatedEventHandlerImplTestInterface;
import com.omgservers.testDataFactory.TestDataFactory;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@Slf4j
@QuarkusTest
class TenantLobbyRefCreatedEventHandlerImplTest extends Assertions {

    @Inject
    VersionLobbyRefCreatedEventHandlerImplTestInterface versionLobbyRefCreatedEventHandler;

    @Inject
    EventModelFactory eventModelFactory;

    @Inject
    TestDataFactory testDataFactory;

    @Test
    void givenHandler_whenRetry_thenFinished() {
        final var tenant = testDataFactory.getTenantTestDataFactory().createTenant();
        final var project = testDataFactory.getTenantTestDataFactory().createProject(tenant);
        final var version = testDataFactory.getTenantTestDataFactory().createVersion(project);
        final var versionLobbyRequest = testDataFactory.getTenantTestDataFactory()
                .createVersionLobbyRequest(version);
        final var lobby = testDataFactory.getLobbyTestDataFactory().createLobby(versionLobbyRequest);
        final var versionLobbyRef = testDataFactory.getTenantTestDataFactory()
                .createVersionLobbyRef(version, lobby);

        final var tenantId = versionLobbyRef.getTenantId();
        final var id = versionLobbyRef.getId();

        final var eventBody = new TenantLobbyRefCreatedEventBodyModel(tenantId, id);
        final var eventModel = eventModelFactory.create(eventBody);

        versionLobbyRefCreatedEventHandler.handle(eventModel);
        log.info("Retry");
        versionLobbyRefCreatedEventHandler.handle(eventModel);
    }
}