package com.omgservers.service.handler.tenant;

import com.omgservers.BaseTestClass;
import com.omgservers.service.event.body.module.tenant.TenantVersionCreatedEventBodyModel;
import com.omgservers.service.factory.system.EventModelFactory;
import com.omgservers.service.handler.tenant.testInterface.VersionCreatedEventHandlerImplTestInterface;
import com.omgservers.testDataFactory.TestDataFactory;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
@QuarkusTest
class TenantVersionCreatedEventHandlerImplTest extends BaseTestClass {

    @Inject
    VersionCreatedEventHandlerImplTestInterface versionCreatedEventHandler;

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

        final var tenantId = version.getTenantId();
        final var id = version.getId();

        final var eventBody = new TenantVersionCreatedEventBodyModel(tenantId, id);
        final var eventModel = eventModelFactory.create(eventBody);

        versionCreatedEventHandler.handle(eventModel);
        log.info("Retry");
        versionCreatedEventHandler.handle(eventModel);
    }
}