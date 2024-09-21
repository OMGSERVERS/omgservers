package com.omgservers.service.handler.tenant;

import com.omgservers.schema.module.tenant.tenant.DeleteTenantRequest;
import com.omgservers.service.event.body.module.tenant.TenantDeletedEventBodyModel;
import com.omgservers.service.factory.system.EventModelFactory;
import com.omgservers.service.handler.tenant.testInterface.TenantDeletedEventHandlerImplTestInterface;
import com.omgservers.service.module.tenant.impl.service.tenantService.testInterface.TenantServiceTestInterface;
import com.omgservers.testDataFactory.TestDataFactory;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@Slf4j
@QuarkusTest
class TenantDeletedEventHandlerImplTest extends Assertions {

    @Inject
    TenantDeletedEventHandlerImplTestInterface tenantDeletedEventHandler;

    @Inject
    TenantServiceTestInterface tenantService;

    @Inject
    EventModelFactory eventModelFactory;

    @Inject
    TestDataFactory testDataFactory;

    @Test
    void givenHandler_whenRetry_thenFinished() {
        final var tenant = testDataFactory.getTenantTestDataFactory().createTenant();
        final var tenantId = tenant.getId();

        final var deleteTenantRequest = new DeleteTenantRequest(tenantId);
        tenantService.deleteTenant(deleteTenantRequest);

        final var eventBody = new TenantDeletedEventBodyModel(tenantId);
        final var eventModel = eventModelFactory.create(eventBody);

        tenantDeletedEventHandler.handle(eventModel);
        log.info("Retry");
        tenantDeletedEventHandler.handle(eventModel);
    }
}