package com.omgservers.service.handler.impl.tenant;

import com.omgservers.BaseTestClass;
import com.omgservers.schema.module.tenant.tenantLobbyResource.DeleteTenantLobbyResourceRequest;
import com.omgservers.service.event.body.module.tenant.TenantLobbyResourceDeletedEventBodyModel;
import com.omgservers.service.factory.system.EventModelFactory;
import com.omgservers.service.handler.impl.tenant.testInterface.TenantLobbyResourceDeletedEventHandlerImplTestInterface;
import com.omgservers.service.shard.tenant.impl.service.tenantService.testInterface.TenantServiceTestInterface;
import com.omgservers.testDataFactory.TestDataFactory;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
@QuarkusTest
class TenantLobbyResourceDeletedEventHandlerImplTest extends BaseTestClass {

    @Inject
    TenantLobbyResourceDeletedEventHandlerImplTestInterface tenantLobbyResourceDeletedEventHandler;

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
        final var stage = testDataFactory.getTenantTestDataFactory().createStage(project);
        final var version = testDataFactory.getTenantTestDataFactory().createTenantVersion(project);
        final var tenantDeployment = testDataFactory.getTenantTestDataFactory()
                .createTenantDeployment(stage, version);
        final var tenantLobbyResource = testDataFactory.getTenantTestDataFactory()
                .createTenantLobbyResource(tenantDeployment);

        final var tenantId = tenantLobbyResource.getTenantId();
        final var id = tenantLobbyResource.getId();

        final var deleteTenantLobbyResourceRequest = new DeleteTenantLobbyResourceRequest(tenantId, id);
        tenantService.execute(deleteTenantLobbyResourceRequest);

        final var eventBody = new TenantLobbyResourceDeletedEventBodyModel(tenantId, id);
        final var eventModel = eventModelFactory.create(eventBody);

        tenantLobbyResourceDeletedEventHandler.handle(eventModel);
        log.info("Retry");
        tenantLobbyResourceDeletedEventHandler.handle(eventModel);
    }
}