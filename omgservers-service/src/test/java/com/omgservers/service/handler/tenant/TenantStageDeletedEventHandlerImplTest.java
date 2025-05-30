package com.omgservers.service.handler.tenant;

import com.omgservers.BaseTestClass;
import com.omgservers.schema.shard.tenant.tenantStage.DeleteTenantStageRequest;
import com.omgservers.service.event.body.module.tenant.TenantStageDeletedEventBodyModel;
import com.omgservers.service.factory.system.EventModelFactory;
import com.omgservers.service.handler.tenant.testInterface.StageDeletedEventHandlerImplTestInterface;
import com.omgservers.service.shard.tenant.service.testInterface.TenantServiceTestInterface;
import com.omgservers.testDataFactory.TestDataFactory;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
@QuarkusTest
class TenantStageDeletedEventHandlerImplTest extends BaseTestClass {

    @Inject
    StageDeletedEventHandlerImplTestInterface stageDeletedEventHandler;

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

        final var tenantId = stage.getTenantId();
        final var id = stage.getId();

        final var deleteTenantStageRequest = new DeleteTenantStageRequest(tenantId, id);
        tenantService.deleteTenantStage(deleteTenantStageRequest);

        final var eventBody = new TenantStageDeletedEventBodyModel(tenantId, id);
        final var eventModel = eventModelFactory.create(eventBody);

        stageDeletedEventHandler.handle(eventModel);
        log.info("Retry");
        stageDeletedEventHandler.handle(eventModel);
    }
}