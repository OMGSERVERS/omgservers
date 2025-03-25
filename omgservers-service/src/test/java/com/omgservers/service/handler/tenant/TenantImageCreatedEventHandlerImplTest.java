package com.omgservers.service.handler.tenant;

import com.omgservers.BaseTestClass;
import com.omgservers.service.event.body.module.tenant.TenantImageCreatedEventBodyModel;
import com.omgservers.service.factory.system.EventModelFactory;
import com.omgservers.service.handler.tenant.testInterface.TenantImageCreatedEventHandlerImplTestInterface;
import com.omgservers.testDataFactory.TestDataFactory;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
@QuarkusTest
class TenantImageCreatedEventHandlerImplTest extends BaseTestClass {

    @Inject
    TenantImageCreatedEventHandlerImplTestInterface tenantImageCreatedEventHandlerImplTestInterface;

    @Inject
    EventModelFactory eventModelFactory;

    @Inject
    TestDataFactory testDataFactory;

    @Test
    void givenHandler_whenRetry_thenFinished() {
        final var testData = testDataFactory.createDefaultTestData();

        final var tenantId = testData.getTenantImage().getTenantId();
        final var id = testData.getTenantImage().getId();

        final var eventBody = new TenantImageCreatedEventBodyModel(tenantId, id);
        final var eventModel = eventModelFactory.create(eventBody);

        tenantImageCreatedEventHandlerImplTestInterface.handle(eventModel);
        log.info("Retry");
        tenantImageCreatedEventHandlerImplTestInterface.handle(eventModel);
    }
}