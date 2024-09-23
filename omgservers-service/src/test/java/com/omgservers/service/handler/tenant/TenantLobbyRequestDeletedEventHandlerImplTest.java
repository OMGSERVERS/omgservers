package com.omgservers.service.handler.tenant;

import com.omgservers.schema.module.tenant.tenantLobbyRequest.DeleteTenantLobbyRequestRequest;
import com.omgservers.service.event.body.module.tenant.TenantLobbyRequestDeletedEventBodyModel;
import com.omgservers.service.factory.system.EventModelFactory;
import com.omgservers.service.handler.tenant.testInterface.VersionLobbyRequestDeletedEventHandlerImplTestInterface;
import com.omgservers.service.module.tenant.impl.service.tenantService.testInterface.TenantServiceTestInterface;
import com.omgservers.testDataFactory.TestDataFactory;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@Slf4j
@QuarkusTest
class TenantLobbyRequestDeletedEventHandlerImplTest extends Assertions {

    @Inject
    VersionLobbyRequestDeletedEventHandlerImplTestInterface versionLobbyRequestDeletedEventHandler;

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
        final var versionLobbyRequest = testDataFactory.getTenantTestDataFactory()
                .createTenantLobbyRequest(tenantDeployment);

        final var tenantId = versionLobbyRequest.getTenantId();
        final var id = versionLobbyRequest.getId();

        final var deleteTenantLobbyRequestRequest = new DeleteTenantLobbyRequestRequest(tenantId, id);
        tenantService.deleteTenantLobbyRequest(deleteTenantLobbyRequestRequest);

        final var eventBody = new TenantLobbyRequestDeletedEventBodyModel(tenantId, id);
        final var eventModel = eventModelFactory.create(eventBody);

        versionLobbyRequestDeletedEventHandler.handle(eventModel);
        log.info("Retry");
        versionLobbyRequestDeletedEventHandler.handle(eventModel);
    }
}