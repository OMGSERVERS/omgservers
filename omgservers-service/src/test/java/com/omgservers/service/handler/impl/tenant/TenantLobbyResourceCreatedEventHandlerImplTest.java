package com.omgservers.service.handler.impl.tenant;

import com.omgservers.BaseTestClass;
import com.omgservers.service.event.body.module.tenant.TenantLobbyResourceCreatedEventBodyModel;
import com.omgservers.service.factory.system.EventModelFactory;
import com.omgservers.service.handler.impl.tenant.testInterface.TenantLobbyResourceCreatedEventHandlerImplTestInterface;
import com.omgservers.testDataFactory.TestDataFactory;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
@QuarkusTest
class TenantLobbyResourceCreatedEventHandlerImplTest extends BaseTestClass {

    @Inject
    TenantLobbyResourceCreatedEventHandlerImplTestInterface tenantLobbyRequestCreatedEventHandler;

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
        final var tenantLobbyResource = testDataFactory.getTenantTestDataFactory()
                .createTenantLobbyResource(tenantDeployment);

        final var tenantId = tenantLobbyResource.getTenantId();
        final var id = tenantLobbyResource.getId();

        final var eventBody = new TenantLobbyResourceCreatedEventBodyModel(tenantId, id);
        final var eventModel = eventModelFactory.create(eventBody);

        tenantLobbyRequestCreatedEventHandler.handle(eventModel);
        log.info("Retry");
        tenantLobbyRequestCreatedEventHandler.handle(eventModel);
    }
}