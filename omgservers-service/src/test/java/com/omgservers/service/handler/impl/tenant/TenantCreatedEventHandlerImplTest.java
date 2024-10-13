package com.omgservers.service.handler.impl.tenant;

import com.omgservers.BaseTestClass;
import com.omgservers.service.event.body.internal.ServiceBootstrapRequestedEventBodyModel;
import com.omgservers.service.event.body.module.tenant.TenantCreatedEventBodyModel;
import com.omgservers.service.factory.system.EventModelFactory;
import com.omgservers.service.handler.impl.internal.testInterface.ServiceBootstrapRequestedEventHandlerImplTestInterface;
import com.omgservers.service.handler.impl.tenant.testInterface.TenantCreatedEventHandlerImplTestInterface;
import com.omgservers.testDataFactory.TestDataFactory;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
@QuarkusTest
class TenantCreatedEventHandlerImplTest extends BaseTestClass {

    @Inject
    TenantCreatedEventHandlerImplTestInterface tenantCreatedEventHandler;

    @Inject
    ServiceBootstrapRequestedEventHandlerImplTestInterface serviceBootstrapRequestedEventHandler;

    @Inject
    EventModelFactory eventModelFactory;

    @Inject
    TestDataFactory testDataFactory;

    @Test
    void givenHandler_whenRetry_thenFinished() {
        serviceBootstrapRequestedEventHandler.handle(eventModelFactory
                .create(new ServiceBootstrapRequestedEventBodyModel()));

        final var testData = testDataFactory.createDefaultTestData();
        final var tenantId = testData.getTenant().getId();

        final var eventBody = new TenantCreatedEventBodyModel(tenantId);
        final var eventModel = eventModelFactory.create(eventBody);

        tenantCreatedEventHandler.handle(eventModel);
        log.info("Retry");
        tenantCreatedEventHandler.handle(eventModel);
    }
}