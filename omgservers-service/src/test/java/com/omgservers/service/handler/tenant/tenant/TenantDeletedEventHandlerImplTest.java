package com.omgservers.service.handler.tenant.tenant;

import com.omgservers.model.dto.tenant.DeleteTenantRequest;
import com.omgservers.model.dto.tenant.SyncTenantRequest;
import com.omgservers.model.event.body.module.tenant.TenantDeletedEventBodyModel;
import com.omgservers.service.factory.system.EventModelFactory;
import com.omgservers.service.factory.tenant.TenantModelFactory;
import com.omgservers.service.handler.tenant.tenant.testInterface.TenantDeletedEventHandlerImplTestInterface;
import com.omgservers.service.module.tenant.impl.service.tenantService.testInterface.TenantServiceTestInterface;
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
    TenantModelFactory tenantModelFactory;

    @Test
    void givenHandler_whenRetry_thenFinished() {
        final var tenant = tenantModelFactory.create();

        final var syncTenantRequest = new SyncTenantRequest(tenant);
        tenantService.syncTenant(syncTenantRequest);

        final var deleteTenantRequest = new DeleteTenantRequest(tenant.getId());
        tenantService.deleteTenant(deleteTenantRequest);

        final var eventBody = new TenantDeletedEventBodyModel(tenant.getId());
        final var eventModel = eventModelFactory.create(eventBody);

        tenantDeletedEventHandler.handle(eventModel);
        log.info("Retry");
        tenantDeletedEventHandler.handle(eventModel);
    }
}