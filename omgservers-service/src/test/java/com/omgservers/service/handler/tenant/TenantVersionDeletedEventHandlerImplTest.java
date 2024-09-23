package com.omgservers.service.handler.tenant;

import com.omgservers.schema.module.tenant.tenantVersion.DeleteTenantVersionRequest;
import com.omgservers.service.event.body.module.tenant.TenantVersionDeletedEventBodyModel;
import com.omgservers.service.factory.system.EventModelFactory;
import com.omgservers.service.handler.tenant.testInterface.VersionDeletedEventHandlerImplTestInterface;
import com.omgservers.service.module.tenant.impl.service.tenantService.testInterface.TenantServiceTestInterface;
import com.omgservers.testDataFactory.TestDataFactory;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@Slf4j
@QuarkusTest
class TenantVersionDeletedEventHandlerImplTest extends Assertions {

    @Inject
    VersionDeletedEventHandlerImplTestInterface versionDeletedEventHandler;

    @Inject
    TenantServiceTestInterface tenantService;

    @Inject
    EventModelFactory eventModelFactory;

    @Inject
    TestDataFactory testDataFactory;

    @Test
    void givenHandler_whenRetry_thenFinished() {
        final var tenant = testDataFactory.getTenantTestDataFactory().createTenant();
        final var project = testDataFactory.getTenantTestDataFactory().createTenantProject(tenant);
        final var version = testDataFactory.getTenantTestDataFactory().createTenantVersion(project);

        final var tenantId = version.getTenantId();
        final var id = version.getId();

        final var deleteTenantVersionRequest = new DeleteTenantVersionRequest(tenantId, id);
        tenantService.deleteTenantVersion(deleteTenantVersionRequest);

        final var eventBody = new TenantVersionDeletedEventBodyModel(tenantId, id);
        final var eventModel = eventModelFactory.create(eventBody);

        versionDeletedEventHandler.handle(eventModel);
        log.info("Retry");
        versionDeletedEventHandler.handle(eventModel);
    }
}