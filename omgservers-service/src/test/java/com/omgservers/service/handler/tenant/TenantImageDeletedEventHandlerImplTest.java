package com.omgservers.service.handler.tenant;

import com.omgservers.BaseTestClass;
import com.omgservers.schema.shard.tenant.tenantImage.DeleteTenantImageRequest;
import com.omgservers.service.event.body.module.tenant.TenantImageDeletedEventBodyModel;
import com.omgservers.service.factory.system.EventModelFactory;
import com.omgservers.service.handler.tenant.testInterface.TenantImageDeletedEventHandlerImplTestInterface;
import com.omgservers.service.shard.tenant.service.testInterface.TenantServiceTestInterface;
import com.omgservers.testDataFactory.TestDataFactory;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
@QuarkusTest
class TenantImageDeletedEventHandlerImplTest extends BaseTestClass {

    @Inject
    TenantImageDeletedEventHandlerImplTestInterface tenantImageDeletedEventHandlerImplTestInterface;

    @Inject
    TenantServiceTestInterface tenantService;

    @Inject
    EventModelFactory eventModelFactory;

    @Inject
    TestDataFactory testDataFactory;

    @Test
    void givenHandler_whenRetry_thenFinished() {
        final var testData = testDataFactory.createDefaultTestData();

        final var tenantId = testData.getTenantImage().getTenantId();
        final var id = testData.getTenantImage().getId();

        final var deleteTenantImageRequest = new DeleteTenantImageRequest(tenantId, id);
        tenantService.deleteTenantImage(deleteTenantImageRequest);

        final var eventBody = new TenantImageDeletedEventBodyModel(tenantId, id);
        final var eventModel = eventModelFactory.create(eventBody);

        tenantImageDeletedEventHandlerImplTestInterface.handle(eventModel);
        log.info("Retry");
        tenantImageDeletedEventHandlerImplTestInterface.handle(eventModel);
    }
}