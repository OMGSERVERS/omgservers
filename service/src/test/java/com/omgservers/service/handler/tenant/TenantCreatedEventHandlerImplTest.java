package com.omgservers.service.handler.tenant;

import com.omgservers.schema.event.body.module.tenant.TenantCreatedEventBodyModel;
import com.omgservers.service.factory.system.EventModelFactory;
import com.omgservers.service.handler.tenant.testInterface.TenantCreatedEventHandlerImplTestInterface;
import com.omgservers.service.server.service.testInterface.BootstrapServiceTestInterface;
import com.omgservers.testDataFactory.TestDataFactory;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@Slf4j
@QuarkusTest
class TenantCreatedEventHandlerImplTest extends Assertions {

    @Inject
    BootstrapServiceTestInterface bootstrapService;

    @Inject
    TenantCreatedEventHandlerImplTestInterface tenantCreatedEventHandler;

    @Inject
    EventModelFactory eventModelFactory;

    @Inject
    TestDataFactory testDataFactory;

    @Test
    void givenHandler_whenRetry_thenFinished() {
        bootstrapService.bootstrap();

        final var tenant = testDataFactory.getTenantTestDataFactory().createTenant();
        final var tenantId = tenant.getId();

        final var eventBody = new TenantCreatedEventBodyModel(tenantId);
        final var eventModel = eventModelFactory.create(eventBody);

        tenantCreatedEventHandler.handle(eventModel);
        log.info("Retry");
        tenantCreatedEventHandler.handle(eventModel);
    }
}