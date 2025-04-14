package com.omgservers.service.handler.tenant;

import com.omgservers.BaseTestClass;
import com.omgservers.schema.shard.tenant.tenant.DeleteTenantRequest;
import com.omgservers.service.event.body.module.tenant.TenantDeletedEventBodyModel;
import com.omgservers.service.factory.system.EventModelFactory;
import com.omgservers.service.handler.tenant.testInterface.TenantDeletedEventHandlerImplTestInterface;
import com.omgservers.service.shard.tenant.service.testInterface.TenantServiceTestInterface;
import com.omgservers.testDataFactory.TestDataFactory;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
@QuarkusTest
class TenantDeletedEventHandlerImplTest extends BaseTestClass {

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
        final var testData = testDataFactory.createDefaultTestData();
        final var tenantId = testData.getTenant().getId();

        final var deleteTenantRequest = new DeleteTenantRequest(tenantId);
        tenantService.deleteTenant(deleteTenantRequest);

        final var eventBody = new TenantDeletedEventBodyModel(tenantId);
        final var eventModel = eventModelFactory.create(eventBody);

        tenantDeletedEventHandler.handle(eventModel);
        log.info("Retry");
        tenantDeletedEventHandler.handle(eventModel);
    }
}