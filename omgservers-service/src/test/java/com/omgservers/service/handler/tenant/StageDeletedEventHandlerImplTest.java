package com.omgservers.service.handler.tenant;

import com.omgservers.schema.module.tenant.DeleteStageRequest;
import com.omgservers.schema.event.body.module.tenant.StageDeletedEventBodyModel;
import com.omgservers.service.factory.system.EventModelFactory;
import com.omgservers.service.handler.tenant.testInterface.StageDeletedEventHandlerImplTestInterface;
import com.omgservers.service.module.tenant.impl.service.stageService.testInterface.StageServiceTestInterface;
import com.omgservers.testDataFactory.TestDataFactory;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@Slf4j
@QuarkusTest
class StageDeletedEventHandlerImplTest extends Assertions {

    @Inject
    StageDeletedEventHandlerImplTestInterface stageDeletedEventHandler;

    @Inject
    StageServiceTestInterface stageService;

    @Inject
    EventModelFactory eventModelFactory;

    @Inject
    TestDataFactory testDataFactory;

    @Test
    void givenHandler_whenRetry_thenFinished() {
        final var tenant = testDataFactory.getTenantTestDataFactory().createTenant();
        final var project = testDataFactory.getTenantTestDataFactory().createProject(tenant);
        final var stage = testDataFactory.getTenantTestDataFactory().createStage(project);

        final var tenantId = stage.getTenantId();
        final var id = stage.getId();

        final var deleteStageRequest = new DeleteStageRequest(tenantId, id);
        stageService.deleteStage(deleteStageRequest);

        final var eventBody = new StageDeletedEventBodyModel(tenantId, id);
        final var eventModel = eventModelFactory.create(eventBody);

        stageDeletedEventHandler.handle(eventModel);
        log.info("Retry");
        stageDeletedEventHandler.handle(eventModel);
    }
}