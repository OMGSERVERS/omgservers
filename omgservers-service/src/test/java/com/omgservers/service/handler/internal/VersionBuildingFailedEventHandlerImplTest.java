package com.omgservers.service.handler.internal;

import com.omgservers.service.event.body.internal.VersionBuildingFailedEventBodyModel;
import com.omgservers.service.factory.system.EventModelFactory;
import com.omgservers.service.handler.internal.testInterface.VersionBuildingFailedEventHandlerImplTestInterface;
import com.omgservers.testDataFactory.TestDataFactory;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@Slf4j
@QuarkusTest
class VersionBuildingFailedEventHandlerImplTest extends Assertions {

    @Inject
    VersionBuildingFailedEventHandlerImplTestInterface versionBuildingFailedEventHandlerImplTestInterface;

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

        final var eventBody = new VersionBuildingFailedEventBodyModel(tenant.getId(), version.getId());
        final var eventModel = eventModelFactory.create(eventBody);

        versionBuildingFailedEventHandlerImplTestInterface.handle(eventModel);
        log.info("Retry");
        versionBuildingFailedEventHandlerImplTestInterface.handle(eventModel);
    }
}