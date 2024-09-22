package com.omgservers.service.handler.internal;

import com.omgservers.service.event.body.internal.VersionBuildingFinishedEventBodyModel;
import com.omgservers.service.factory.system.EventModelFactory;
import com.omgservers.service.handler.internal.testInterface.VersionBuildingFinishedEventHandlerImplTestInterface;
import com.omgservers.testDataFactory.TestDataFactory;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@Slf4j
@QuarkusTest
class VersionBuildingFinishedEventHandlerImplTest extends Assertions {

    @Inject
    VersionBuildingFinishedEventHandlerImplTestInterface versionBuildingFinishedEventHandlerImplTestInterface;

    @Inject
    EventModelFactory eventModelFactory;

    @Inject
    TestDataFactory testDataFactory;

    @Test
    void givenHandler_whenRetry_thenFinished() {
        final var tenant = testDataFactory.getTenantTestDataFactory().createTenant();
        final var project = testDataFactory.getTenantTestDataFactory().createProject(tenant);
        final var version = testDataFactory.getTenantTestDataFactory().createVersion(project);

        final var eventBody = new VersionBuildingFinishedEventBodyModel(tenant.getId(), version.getId());
        final var eventModel = eventModelFactory.create(eventBody);

        versionBuildingFinishedEventHandlerImplTestInterface.handle(eventModel);
        log.info("Retry");
        versionBuildingFinishedEventHandlerImplTestInterface.handle(eventModel);
    }
}