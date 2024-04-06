package com.omgservers.service.handler.tenant.tenant;

import com.omgservers.model.dto.tenant.SyncTenantRequest;
import com.omgservers.model.event.body.module.tenant.TenantCreatedEventBodyModel;
import com.omgservers.service.factory.system.EventModelFactory;
import com.omgservers.service.factory.tenant.TenantModelFactory;
import com.omgservers.service.handler.tenant.tenant.testInterface.TenantCreatedEventHandlerImplTestInterface;
import com.omgservers.service.module.tenant.impl.service.tenantService.testInterface.TenantServiceTestInterface;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@Slf4j
@QuarkusTest
class TenantCreatedEventHandlerImplTest extends Assertions {

    @Inject
    TenantCreatedEventHandlerImplTestInterface tenantCreatedEventHandler;

    @Inject
    TenantServiceTestInterface tenantService;

    @Inject
    EventModelFactory eventModelFactory;

    @Inject
    TenantModelFactory tenantModelFactory;

    @Test
    void givenHandler_whenRetry_thenFinished() {
        final var tenant = tenantModelFactory.create();

        final var syncTenantRequest = new SyncTenantRequest(tenant);
        tenantService.syncTenant(syncTenantRequest);

        final var eventBody = new TenantCreatedEventBodyModel(tenant.getId());
        final var eventModel = eventModelFactory.create(eventBody);

        tenantCreatedEventHandler.handle(eventModel);
        log.info("Retry");
        tenantCreatedEventHandler.handle(eventModel);
    }
}