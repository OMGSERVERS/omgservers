package com.omgservers.service.handler.impl.tenant;

import com.omgservers.BaseTestClass;
import com.omgservers.schema.module.tenant.tenantLobbyRef.DeleteTenantLobbyRefRequest;
import com.omgservers.service.event.body.module.tenant.TenantLobbyRefDeletedEventBodyModel;
import com.omgservers.service.factory.system.EventModelFactory;
import com.omgservers.service.handler.impl.tenant.testInterface.TenantLobbyRefDeletedEventHandlerImplTestInterface;
import com.omgservers.service.shard.tenant.impl.service.tenantService.testInterface.TenantServiceTestInterface;
import com.omgservers.testDataFactory.TestDataFactory;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
@QuarkusTest
class TenantLobbyRefDeletedEventHandlerImplTest extends BaseTestClass {

    @Inject
    TenantLobbyRefDeletedEventHandlerImplTestInterface versionLobbyRefDeletedEventHandler;

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
                .createTenantLobbyResource(tenantDeployment);
        final var lobby = testDataFactory.getLobbyTestDataFactory().createLobby(versionLobbyRequest);
        final var versionLobbyRef = testDataFactory.getTenantTestDataFactory()
                .createTenantLobbyRef(tenantDeployment, lobby);

        final var tenantId = versionLobbyRef.getTenantId();
        final var id = versionLobbyRef.getId();

        final var deleteTenantLobbyRefRequest = new DeleteTenantLobbyRefRequest(tenantId, id);
        tenantService.deleteTenantLobbyRef(deleteTenantLobbyRefRequest);

        final var eventBody = new TenantLobbyRefDeletedEventBodyModel(tenantId, id);
        final var eventModel = eventModelFactory.create(eventBody);

        versionLobbyRefDeletedEventHandler.handle(eventModel);
        log.info("Retry");
        versionLobbyRefDeletedEventHandler.handle(eventModel);
    }
}