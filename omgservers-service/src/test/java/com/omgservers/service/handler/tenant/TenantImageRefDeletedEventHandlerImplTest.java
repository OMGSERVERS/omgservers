package com.omgservers.service.handler.tenant;

import com.omgservers.schema.module.tenant.tenantImageRef.DeleteTenantImageRefRequest;
import com.omgservers.service.event.body.module.tenant.TenantImageRefDeletedEventBodyModel;
import com.omgservers.service.factory.system.EventModelFactory;
import com.omgservers.service.handler.tenant.testInterface.VersionImageRefDeletedEventHandlerImplTestInterface;
import com.omgservers.service.module.tenant.impl.service.tenantService.testInterface.TenantServiceTestInterface;
import com.omgservers.testDataFactory.TestDataFactory;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@Slf4j
@QuarkusTest
class TenantImageRefDeletedEventHandlerImplTest extends Assertions {

    @Inject
    VersionImageRefDeletedEventHandlerImplTestInterface versionImageRefDeletedEventHandlerImplTestInterface;

    @Inject
    TenantServiceTestInterface tenantService;

    @Inject
    EventModelFactory eventModelFactory;

    @Inject
    TestDataFactory testDataFactory;

    @Test
    void givenHandler_whenRetry_thenFinished() {
        final var testData = testDataFactory.createTestData();

        final var tenantId = testData.getTenantImageRef().getTenantId();
        final var id = testData.getTenantImageRef().getId();

        final var deleteTenantImageRefRequest = new DeleteTenantImageRefRequest(tenantId, id);
        tenantService.deleteTenantImageRef(deleteTenantImageRefRequest);

        final var eventBody = new TenantImageRefDeletedEventBodyModel(tenantId, id);
        final var eventModel = eventModelFactory.create(eventBody);

        versionImageRefDeletedEventHandlerImplTestInterface.handle(eventModel);
        log.info("Retry");
        versionImageRefDeletedEventHandlerImplTestInterface.handle(eventModel);
    }
}