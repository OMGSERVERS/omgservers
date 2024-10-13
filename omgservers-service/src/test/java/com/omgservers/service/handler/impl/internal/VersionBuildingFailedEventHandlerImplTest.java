package com.omgservers.service.handler.impl.internal;

import com.omgservers.BaseTestClass;
import com.omgservers.service.event.body.internal.VersionBuildingFailedEventBodyModel;
import com.omgservers.service.factory.system.EventModelFactory;
import com.omgservers.service.handler.impl.internal.testInterface.VersionBuildingFailedEventHandlerImplTestInterface;
import com.omgservers.testDataFactory.TestDataFactory;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
@QuarkusTest
class VersionBuildingFailedEventHandlerImplTest extends BaseTestClass {

    @Inject
    VersionBuildingFailedEventHandlerImplTestInterface versionBuildingFailedEventHandlerImplTestInterface;

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

        final var eventBody = new VersionBuildingFailedEventBodyModel(tenant.getId(), tenantVersion.getId());
        final var eventModel = eventModelFactory.create(eventBody);

        versionBuildingFailedEventHandlerImplTestInterface.handle(eventModel);
        log.info("Retry");
        versionBuildingFailedEventHandlerImplTestInterface.handle(eventModel);
    }
}