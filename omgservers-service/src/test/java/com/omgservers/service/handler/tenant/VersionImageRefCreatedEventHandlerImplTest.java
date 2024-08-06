package com.omgservers.service.handler.tenant;

import com.omgservers.schema.event.body.module.tenant.VersionImageRefCreatedEventBodyModel;
import com.omgservers.service.factory.system.EventModelFactory;
import com.omgservers.service.handler.tenant.testInterface.VersionImageRefCreatedEventHandlerImplTestInterface;
import com.omgservers.testDataFactory.TestDataFactory;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@Slf4j
@QuarkusTest
class VersionImageRefCreatedEventHandlerImplTest extends Assertions {

    @Inject
    VersionImageRefCreatedEventHandlerImplTestInterface versionImageRefCreatedEventHandlerImplTestInterface;

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
        final var versionImageRef = testDataFactory.getTenantTestDataFactory().createVersionImageRef(version);

        final var tenantId = versionImageRef.getTenantId();
        final var id = versionImageRef.getId();

        final var eventBody = new VersionImageRefCreatedEventBodyModel(tenantId, id);
        final var eventModel = eventModelFactory.create(eventBody);

        versionImageRefCreatedEventHandlerImplTestInterface.handle(eventModel);
        log.info("Retry");
        versionImageRefCreatedEventHandlerImplTestInterface.handle(eventModel);
    }
}