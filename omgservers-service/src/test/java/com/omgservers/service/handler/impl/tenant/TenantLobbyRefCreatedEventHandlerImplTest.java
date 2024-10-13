package com.omgservers.service.handler.impl.tenant;

import com.omgservers.BaseTestClass;
import com.omgservers.service.event.body.module.tenant.TenantLobbyRefCreatedEventBodyModel;
import com.omgservers.service.factory.system.EventModelFactory;
import com.omgservers.service.handler.impl.tenant.testInterface.VersionLobbyRefCreatedEventHandlerImplTestInterface;
import com.omgservers.testDataFactory.TestDataFactory;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
@QuarkusTest
class TenantLobbyRefCreatedEventHandlerImplTest extends BaseTestClass {

    @Inject
    VersionLobbyRefCreatedEventHandlerImplTestInterface versionLobbyRefCreatedEventHandler;

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
        final var tenantDeployment = testDataFactory.getTenantTestDataFactory()
                .createTenantDeployment(stage, version);
        final var versionLobbyRequest = testDataFactory.getTenantTestDataFactory()
                .createTenantLobbyRequest(tenantDeployment);
        final var lobby = testDataFactory.getLobbyTestDataFactory().createLobby(versionLobbyRequest);
        final var versionLobbyRef = testDataFactory.getTenantTestDataFactory()
                .createTenantLobbyRef(tenantDeployment, lobby);

        final var tenantId = versionLobbyRef.getTenantId();
        final var id = versionLobbyRef.getId();

        final var eventBody = new TenantLobbyRefCreatedEventBodyModel(tenantId, id);
        final var eventModel = eventModelFactory.create(eventBody);

        versionLobbyRefCreatedEventHandler.handle(eventModel);
        log.info("Retry");
        versionLobbyRefCreatedEventHandler.handle(eventModel);
    }
}