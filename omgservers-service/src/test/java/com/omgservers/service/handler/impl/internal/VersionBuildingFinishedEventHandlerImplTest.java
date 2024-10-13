package com.omgservers.service.handler.impl.internal;

import com.omgservers.BaseTestClass;
import com.omgservers.service.event.body.internal.VersionBuildingFinishedEventBodyModel;
import com.omgservers.service.factory.system.EventModelFactory;
import com.omgservers.service.handler.impl.internal.testInterface.VersionBuildingFinishedEventHandlerImplTestInterface;
import com.omgservers.testDataFactory.TestDataFactory;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
@QuarkusTest
class VersionBuildingFinishedEventHandlerImplTest extends BaseTestClass {

    @Inject
    VersionBuildingFinishedEventHandlerImplTestInterface versionBuildingFinishedEventHandlerImplTestInterface;

    @Inject
    EventModelFactory eventModelFactory;

    @Inject
    TestDataFactory testDataFactory;

    @Test
    void givenHandler_whenRetry_thenFinished() {
        final var tenant = testDataFactory.getTenantTestDataFactory().createTenant();
        final var tenantProject = testDataFactory.getTenantTestDataFactory().createTenantProject(tenant);
        final var tenantVersion = testDataFactory.getTenantTestDataFactory()
                .createTenantVersion(tenantProject);

        final var eventBody = new VersionBuildingFinishedEventBodyModel(tenant.getId(), tenantVersion.getId());
        final var eventModel = eventModelFactory.create(eventBody);

        versionBuildingFinishedEventHandlerImplTestInterface.handle(eventModel);
        log.info("Retry");
        versionBuildingFinishedEventHandlerImplTestInterface.handle(eventModel);
    }
}