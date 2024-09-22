package com.omgservers.service.handler.tenant;

import com.omgservers.service.event.body.module.tenant.TenantImageRefCreatedEventBodyModel;
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
class TenantImageRefCreatedEventHandlerImplTest extends Assertions {

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
        final var version = testDataFactory.getTenantTestDataFactory().createVersion(project);
        final var versionImageRef = testDataFactory.getTenantTestDataFactory().createVersionImageRef(version);

        final var tenantId = versionImageRef.getTenantId();
        final var id = versionImageRef.getId();

        final var eventBody = new TenantImageRefCreatedEventBodyModel(tenantId, id);
        final var eventModel = eventModelFactory.create(eventBody);

        versionImageRefCreatedEventHandlerImplTestInterface.handle(eventModel);
        log.info("Retry");
        versionImageRefCreatedEventHandlerImplTestInterface.handle(eventModel);
    }
}